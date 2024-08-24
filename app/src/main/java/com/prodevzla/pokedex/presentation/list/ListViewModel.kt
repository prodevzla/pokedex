package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.Filter
import com.prodevzla.pokedex.domain.GetFiltersUseCase
import com.prodevzla.pokedex.domain.GetPokemonGenerationsUseCase
import com.prodevzla.pokedex.domain.GetPokemonTypesUseCase
import com.prodevzla.pokedex.domain.GetPokemonsUseCase
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.Result
import com.prodevzla.pokedex.model.domain.PokemonType
import com.prodevzla.pokedex.model.filterIf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getPokemonsUseCase: GetPokemonsUseCase,
    getPokemonTypesUseCase: GetPokemonTypesUseCase,
    getFiltersUseCase: GetFiltersUseCase,
    getPokemonGenerationsUseCase: GetPokemonGenerationsUseCase,
) : ViewModel() {


    private val _pokemonList: Flow<Result<List<Pokemon>>> =
        getPokemonsUseCase.invoke()

    private val _generations: Flow<Result<List<PokemonGeneration>>> =
        getPokemonGenerationsUseCase.invoke()

    private val _types: Flow<Result<List<PokemonType>>> =
        getPokemonTypesUseCase.invoke()

    private val _generationFilter = MutableStateFlow(0)

    private val _typeFilter = MutableStateFlow(0)

    private val _showFilterDialog: MutableStateFlow<Filter?> = MutableStateFlow(null)

    private val _filtersCombination: Flow<List<Filter>?> = combine(
        _generations, _generationFilter, _types, _typeFilter
    ) { generations, generationFilter, types, typeFilter  ->
        when {
            types is Result.Success && generations is Result.Success -> {
                getFiltersUseCase.invoke(
                    pokemonGenerations = generations.data,
                    generationFilter = generationFilter,
                    pokemonTypes = types.data,
                    typeFilter = typeFilter,
                    onClickGeneration = ::onClickGeneration,
                    onClickType = ::onClickType
                )
            }

            else -> { null }
        }

    }

    val uiState: StateFlow<ListState> =
        combine(
            _pokemonList,
            _filtersCombination,
            _showFilterDialog
        ) { pokemonList, filters, showFilterDialog ->
            when {
                pokemonList is Result.Success && filters != null -> {
                    ListState.Content(
                        pokemonList = pokemonList.data.filterIf(_typeFilter.value != 0) {
                            it.types.contains(
                                _typeFilter.value
                            )
                        }.filterIf(_generationFilter.value != 0) {
                            it.generation == _generationFilter.value
                        },
                        filters = filters,
                        showFilterDialog = showFilterDialog,
                    )
                }

                else -> {
                    ListState.Error
                }
            }


        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ListState.Loading
            )

    private fun onClickGeneration(generation: Int) {
        _generationFilter.value = generation
    }

    private fun onClickType(type: Int) {
        _typeFilter.value = type
    }

    fun onClickFilter(filter: Filter?) {
        _showFilterDialog.value = filter
    }

}

sealed interface ListState {
    data object Loading : ListState

    data class Content(
        val pokemonList: List<Pokemon>,
        val filters: List<Filter>,
        val showFilterDialog: Filter? = null,
    ) : ListState

    data object Error : ListState
}

