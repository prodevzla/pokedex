package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.AnalyticsEvent.ClickEvent
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.Sort
import com.prodevzla.pokedex.domain.model.SortBy
import com.prodevzla.pokedex.domain.model.SortOrder
import com.prodevzla.pokedex.domain.usecase.GetFiltersUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonsUseCase
import com.prodevzla.pokedex.domain.usecase.ToggleSavePokemonUseCase
import com.prodevzla.pokedex.domain.usecase.TrackEventUseCase
import com.prodevzla.pokedex.domain.usecase.filterIf
import com.prodevzla.pokedex.presentation.list.model.ListState
import com.prodevzla.pokedex.presentation.util.RetryableFlowTrigger
import com.prodevzla.pokedex.presentation.util.retryableFlow
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getPokemonsUseCase: GetPokemonsUseCase,
    getFiltersUseCase: GetFiltersUseCase,
    private val trackEventUseCase: TrackEventUseCase,
    private val toggleSavePokemonUseCase: ToggleSavePokemonUseCase,
) : ViewModel() {

    private val _generationFilter: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get<Int>(KEY_FILTER_GEN) ?: DEFAULT_FILTER
    )
    private val _typeFilter = MutableStateFlow(
        savedStateHandle.get<Int>(KEY_FILTER_TYPE) ?: DEFAULT_FILTER
    )

    private val _sort = MutableStateFlow(
        savedStateHandle.get<Sort>(KEY_SORT) ?: Sort()
    )

    private val _search = MutableStateFlow(
        savedStateHandle.get<String>(KEY_SEARCH) ?: DEFAULT_SEARCH
    )

    private val retryableFlowTrigger = RetryableFlowTrigger()

    private val _filters = getFiltersUseCase.invoke(
        generationFilter = _generationFilter,
        typeFilter = _typeFilter,
    )

    val uiState: StateFlow<ListState> =
        combine(
            retryableFlowTrigger.retryableFlow {
                getPokemonsUseCase.invoke()//.onStart { emit(Result.Loading) }
            },
            _filters,
            _sort,
            _search
        ) { pokemonList, filters, sort, search ->
            when (pokemonList) {
                is Result.Success -> {
                    ListState.Content(
                        pokemonList = filterPokemon(
                            pokemonList = pokemonList.data,
                            generationFilter = _generationFilter.value,
                            typeFilter = _typeFilter.value,
                            sort = _sort.value,
                            search = _search.value
                        ),
                        filters = filters,
                        sort = sort,
                        search = search
                    )
                }

                is Result.Loading -> ListState.Loading

                else -> ListState.Error
            }
        }.toStateFlow(viewModelScope, ListState.Loading)

    private fun filterPokemon(
        pokemonList: List<Pokemon>,
        generationFilter: Int,
        typeFilter: Int,
        sort: Sort,
        search: String,
    ): List<Pokemon> {
        return pokemonList
            .filterIf(generationFilter != DEFAULT_FILTER) {
                it.generation == generationFilter
            }
            .filterIf(typeFilter != DEFAULT_FILTER) {
                it.types.any { type -> type.id == typeFilter }
            }
            .sortedWith(compareBy<Pokemon> {
                when (sort.sortBy) {
                    SortBy.ID -> it.id
                    SortBy.Name -> it.name
                }
            }.let {
                if (sort.sortOrder == SortOrder.Descending) it.reversed() else it
            }).filter { it.name.contains(search) }
    }

    fun onEvent(event: ListScreenEvent) {
        trackEventUseCase.invoke(ClickEvent(event.eventTag, event.value))
        when (event) {
            is ListScreenEvent.SelectFilter -> {
                when (event.filterType) {
                    FilterType.GENERATION -> {
                        savedStateHandle[KEY_FILTER_GEN] = event.selection.id
                        _generationFilter.value = event.selection.id
                    }
                    FilterType.TYPE -> {
                        savedStateHandle[KEY_FILTER_TYPE] = event.selection.id
                        _typeFilter.value = event.selection.id
                    }
                }
            }

            is ListScreenEvent.SelectSort -> {
                savedStateHandle[KEY_SORT] = event.selection
                _sort.value = event.selection
            }
            is ListScreenEvent.SearchPokemon -> {
                savedStateHandle[KEY_SEARCH] = event.input
                _search.value = event.input
            }

            is ListScreenEvent.ClickTryAgain -> retryableFlowTrigger.retry()
            is ListScreenEvent.ToggleSave -> {
                viewModelScope.launch(Dispatchers.IO) {
                    toggleSavePokemonUseCase.invoke(event.pokemon.id)
                }
            }
            else -> {}
        }
    }

    companion object {
        private const val DEFAULT_FILTER = 0
        private const val DEFAULT_SEARCH = ""
        private const val KEY_SEARCH = "search"
        private const val KEY_SORT = "sort"
        private const val KEY_FILTER_GEN = "filterGen"
        private const val KEY_FILTER_TYPE = "filterType"
    }

}
