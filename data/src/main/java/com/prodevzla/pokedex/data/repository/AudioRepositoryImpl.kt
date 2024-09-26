package com.prodevzla.pokedex.data.repository

import com.prodevzla.pokedex.data.source.local.audioPlayer.MediaPlayer
import com.prodevzla.pokedex.data.source.local.audioPlayer.TTSPlayer
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.repository.AudioRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRepositoryImpl @Inject constructor(
    private val ttsPlayer: TTSPlayer,
    private val mediaPlayer: MediaPlayer,
): AudioRepository {

    override fun observeVoiceoverState() = ttsPlayer.state

    override fun toggleTTSPlayer(audio: String?) {
        if (ttsPlayer.state.value == AudioPlaybackState.IDLE && audio != null) {
            ttsPlayer.play(audio)
            return
        }
        ttsPlayer.stop()
    }

    override fun observeMediaPlayerState() = mediaPlayer.state

    override fun toggleMPlayer(audio: String?) {
        if (mediaPlayer.state.value == AudioPlaybackState.IDLE && audio != null) {
            mediaPlayer.play(audio)
            return
        }
        mediaPlayer.stop()
    }

}
