package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.Filter
import com.prodevzla.pokedex.domain.GetFiltersUseCase
import com.prodevzla.pokedex.domain.GetPokemonTypesUseCase
import com.prodevzla.pokedex.domain.GetPokemonsUseCase
import com.prodevzla.pokedex.model.domain.Pokemon
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
) : ViewModel() {


    private val _pokemonList: Flow<Result<List<Pokemon>>> =
        getPokemonsUseCase.invoke()

    private val _types: Flow<Result<List<PokemonType>>> =
        getPokemonTypesUseCase.invoke()

    private val _typeFilter = MutableStateFlow(0)

    val uiState: StateFlow<ListState> =
        combine(_pokemonList, _types, _typeFilter) { pokemonList, types, typeFilter ->
            when {
                pokemonList is Result.Success && types is Result.Success -> {
                    ListState.Content(
                        pokemonList = pokemonList.data.filterIf(_typeFilter.value != 0) {
                            it.types.contains(
                                typeFilter
                            )
                        },
                        pokemonTypes = types.data,
                        filters = getFiltersUseCase.invoke(types.data, typeFilter),
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

    fun onClickType(type: Int) {
        _typeFilter.value = type
    }

}

sealed interface ListState {
    data object Loading : ListState

    data class Content(
        val pokemonList: List<Pokemon>,
        val pokemonTypes: List<PokemonType>,
        val filters: List<Filter>,
    ) : ListState

    data object Error : ListState
}
