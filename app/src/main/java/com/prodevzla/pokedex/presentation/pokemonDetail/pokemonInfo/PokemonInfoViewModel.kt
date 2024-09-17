package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetPokemonInfoUseCase
import com.prodevzla.pokedex.domain.usecase.PokemonInfoUI
import com.prodevzla.pokedex.presentation.pokemonDetail.base.BaseAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    pokemonInfoUseCase: GetPokemonInfoUseCase
) : BaseAppViewModel(application, savedStateHandle) {

    val uiState = pokemonInfoUseCase.invoke(pokemon.id).map { response ->
        when (response) {
            Result.Loading -> PokemonInfoUiState.Loading
            is Result.Error -> PokemonInfoUiState.Error
            is Result.Success -> PokemonInfoUiState.Content(
                response.data
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PokemonInfoUiState.Loading
    )

}

sealed interface PokemonInfoUiState {
    data object Loading : PokemonInfoUiState
    data object Error : PokemonInfoUiState
    data class Content(val content: PokemonInfoUI) : PokemonInfoUiState
}
