package com.prodevzla.pokedex.domain.repository

import android.net.Uri
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    fun observeVoiceoverState(): Flow<AudioPlaybackState>

    fun playAudioTTS(audio: String)

    fun stopAudioTTS()

    fun observeMediaPlayerState(): Flow<AudioPlaybackState>

    fun playAudioMP(audio: Uri)

    fun stopAudioMP()

}
