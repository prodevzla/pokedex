package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.usecase.GetPokemonUseCase
import com.prodevzla.pokedex.domain.usecase.ToggleSavePokemonUseCase
import com.prodevzla.pokedex.presentation.pokemonDetail.base.BasePokemonDetailViewModel
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPokemonUseCase: GetPokemonUseCase,
    private val toggleSavePokemonUseCase: ToggleSavePokemonUseCase,
) : BasePokemonDetailViewModel(savedStateHandle) {

    val uiState: StateFlow<Pokemon?> = getPokemonUseCase.invoke(pokemonId)
        .toStateFlow(viewModelScope, null)

    fun onEvent(event: PokemonDetailEvent) {
        when (event) {
            PokemonDetailEvent.SaveClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    uiState.value?.let {
                        toggleSavePokemonUseCase.invoke(it.id).takeIf { uiState.value != null }
                    }
                }
            }
            else -> {}
        }
    }
}
