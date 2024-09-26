package com.prodevzla.pokedex.domain.repository

import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    fun observeVoiceoverState(): Flow<AudioPlaybackState>

    fun toggleTTSPlayer(audio: String?)

    fun observeMediaPlayerState(): Flow<AudioPlaybackState>

    fun toggleMPlayer(audio: String?)

}
