package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.AnalyticsEvent.ClickEvent
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.Sort
import com.prodevzla.pokedex.domain.model.SortBy
import com.prodevzla.pokedex.domain.model.SortOrder
import com.prodevzla.pokedex.domain.usecase.GetFiltersUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonsUseCase
import com.prodevzla.pokedex.domain.usecase.TrackEventUseCase
import com.prodevzla.pokedex.domain.usecase.filterIf
import com.prodevzla.pokedex.presentation.util.RetryableFlowTrigger
import com.prodevzla.pokedex.presentation.util.retryableFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getPokemonsUseCase: GetPokemonsUseCase,
    getFiltersUseCase: GetFiltersUseCase,
    private val trackEventUseCase: TrackEventUseCase,
) : ViewModel() {

    private val _generationFilter: MutableStateFlow<Int> = MutableStateFlow(DEFAULT_FILTER)
    private val _typeFilter = MutableStateFlow(DEFAULT_FILTER)

    private val _sort = MutableStateFlow(Sort())

    private val _search = MutableStateFlow("")

    private val retryableFlowTrigger = RetryableFlowTrigger()

    private val _filters = getFiltersUseCase.invoke(
        generationFilter = _generationFilter,
        typeFilter = _typeFilter,
    )

    val uiState: StateFlow<ListState> =
        combine(
            retryableFlowTrigger.retryableFlow {
                getPokemonsUseCase.invoke()
            },
            _filters,
            _sort,
            _search
        ) { pokemonList, filters, sort, search ->
            when(pokemonList) {
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
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListState.Loading
        )

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
                    FilterType.GENERATION -> _generationFilter.value = event.selection.id
                    FilterType.TYPE -> _typeFilter.value = event.selection.id
                }
            }

            is ListScreenEvent.SelectSort -> _sort.value = event.selection
            is ListScreenEvent.SearchPokemon -> _search.value = event.input
            is ListScreenEvent.ClickTryAgain -> retryableFlowTrigger.retry()
            else -> {}
        }
    }

    companion object {
        private const val DEFAULT_FILTER = 0
    }

}

/**
 * Represents the different states of the Pokémon list screen.
 */
sealed interface ListState {

    /**
     * Represents the loading state when data is being fetched.
     */
    data object Loading : ListState

    /**
     * Represents the content state when data has been successfully fetched.
     *
     * @property pokemonList The list of Pokémon to display. This list is always present if the state is Content.
     * @property filters The list of filters to apply to the Pokémon list. If the app fails to fetch generations and/or types,
     * the filters will be null, and the Pokémon, if fetched successfully, will still appear.
     */
    data class Content(
        val pokemonList: List<Pokemon>,
        val filters: List<Filter>?,
        val sort: Sort,
        val search: String,
    ) : ListState

    /**
     * Represents the error state when data fails to load.
     */
    data object Error : ListState
}
