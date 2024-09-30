package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetPokemonInfoUseCase
import com.prodevzla.pokedex.domain.usecase.ObserveMediaPlayerUseCase
import com.prodevzla.pokedex.domain.usecase.ObserveVoiceoverPlayerUseCase
import com.prodevzla.pokedex.domain.usecase.PlayMPAudioUseCase
import com.prodevzla.pokedex.domain.usecase.PlayTTSAudioUseCase
import com.prodevzla.pokedex.presentation.pokemonDetail.base.BasePokemonDetailViewModel
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonInfoUiState
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase.TransformPokemonInfoIntoModelViewCase
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pokemonInfoUseCase: GetPokemonInfoUseCase,
    observeVoiceoverPlayerUseCase: ObserveVoiceoverPlayerUseCase,
    private val playTTSAudioUseCase: PlayTTSAudioUseCase,
    observeMediaPlayerUseCase: ObserveMediaPlayerUseCase,
    private val playMPAudioUseCase: PlayMPAudioUseCase,
    private val transformPokemonInfoIntoModelViewCase: TransformPokemonInfoIntoModelViewCase,
) : BasePokemonDetailViewModel(savedStateHandle) {

    val uiState: StateFlow<PokemonInfoUiState> = combine(
        pokemonInfoUseCase.invoke(pokemonId),
        observeVoiceoverPlayerUseCase.invoke(),
        observeMediaPlayerUseCase.invoke()
    ) { infoResponse, voiceoverPlaybackState, mediaPlayerPlaybackState ->
        when (infoResponse) {
            Result.Loading -> PokemonInfoUiState.Loading
            is Result.Error -> PokemonInfoUiState.Error
            is Result.Success ->
                transformPokemonInfoIntoModelViewCase.invoke(
                    pokemonInfo = infoResponse.data,
                    statePlayVoiceover = voiceoverPlaybackState,
                    statePlayCry = mediaPlayerPlaybackState
                )
        }
    }.toStateFlow(viewModelScope, PokemonInfoUiState.Loading)

    fun onEvent(event: PokemonInfoEvent) {
        when (event) {
            is PokemonInfoEvent.TogglePlayVoiceover ->
                viewModelScope.launch {
                    playTTSAudioUseCase.invoke(event.content)
                }

            is PokemonInfoEvent.TogglePlayCry ->
                playMPAudioUseCase.invoke(event.content)

            PokemonInfoEvent.ScreenStopped -> {
                playTTSAudioUseCase.invoke(null)
                playMPAudioUseCase.invoke(null)
            }
        }

    }

}
