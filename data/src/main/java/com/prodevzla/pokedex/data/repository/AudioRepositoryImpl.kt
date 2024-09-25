package com.prodevzla.pokedex.data.repository

import com.prodevzla.pokedex.data.source.local.audioPlayer.MediaPlayer
import com.prodevzla.pokedex.data.source.local.audioPlayer.TTSPlayer
import com.prodevzla.pokedex.domain.repository.AudioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRepositoryImpl @Inject constructor(
    private val ttsPlayer: TTSPlayer,
    private val mediaPlayer: MediaPlayer,
): AudioRepository {

    override fun observeVoiceoverState() = ttsPlayer.state

    override fun playAudioTTS(audio: String) {
        ttsPlayer.play(audio)
    }

    override fun stopAudioTTS() {
        ttsPlayer.stop()
    }

    override fun observeMediaPlayerState() = mediaPlayer.state

    override fun playAudioMP(audio: String) {
        mediaPlayer.play(audio)
    }

    override fun stopAudioMP() {
       mediaPlayer.stop()
    }
}
