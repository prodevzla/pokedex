package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetPokemonInfoUseCase
import com.prodevzla.pokedex.domain.usecase.ObserveMediaPlayerUseCase
import com.prodevzla.pokedex.domain.usecase.ObserveVoiceoverPlayerUseCase
import com.prodevzla.pokedex.domain.usecase.PlayMPAudioUseCase
import com.prodevzla.pokedex.domain.usecase.PlayTTSAudioUseCase
import com.prodevzla.pokedex.domain.usecase.PokemonInfoUI
import com.prodevzla.pokedex.presentation.pokemonDetail.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pokemonInfoUseCase: GetPokemonInfoUseCase,
    observeVoiceoverPlayerUseCase: ObserveVoiceoverPlayerUseCase,
    private val playTTSAudioUseCase: PlayTTSAudioUseCase,
    observeMediaPlayerUseCase: ObserveMediaPlayerUseCase,
    private val playMPAudioUseCase: PlayMPAudioUseCase
) : BaseViewModel(savedStateHandle) {

    val uiState: StateFlow<PokemonInfoUiState> = combine(
        pokemonInfoUseCase.invoke(pokemon.id),
        observeVoiceoverPlayerUseCase.invoke(),
        observeMediaPlayerUseCase.invoke()
    ) { infoResponse, voiceoverPlaybackState, mediaPlayerPlaybackState ->
        when (infoResponse) {
            Result.Loading -> PokemonInfoUiState.Loading
            is Result.Error -> PokemonInfoUiState.Error
            is Result.Success -> PokemonInfoUiState.Content(
                content = infoResponse.data,
                statePlayVoiceover = voiceoverPlaybackState,
                statePlayCry = mediaPlayerPlaybackState
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PokemonInfoUiState.Loading
    )

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

sealed interface PokemonInfoUiState {
    data object Loading : PokemonInfoUiState
    data object Error : PokemonInfoUiState
    data class Content(
        val content: PokemonInfoUI,
        val statePlayVoiceover: AudioPlaybackState = AudioPlaybackState.IDLE,
        val statePlayCry: AudioPlaybackState = AudioPlaybackState.IDLE
    ) : PokemonInfoUiState
}
