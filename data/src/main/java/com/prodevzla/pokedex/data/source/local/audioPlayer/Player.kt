package com.prodevzla.pokedex.data.source.local.audioPlayer

import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Player {

    private val _state = MutableStateFlow(AudioPlaybackState.IDLE)
    val state: StateFlow<AudioPlaybackState> get() = _state.asStateFlow()

    abstract fun play(audio: String)

    abstract fun stop()

    protected fun setPlaying() {
        _state.value = AudioPlaybackState.PLAYING
    }

    protected fun setIdle() {
        _state.value = AudioPlaybackState.IDLE
    }
}
