package com.prodevzla.pokedex.data.source.local.audioPlayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MediaPlayer(val context: Context): Player() {

    private val mediaPlayer = MediaPlayer()

    override fun play(audio: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, Uri.parse(audio))
        mediaPlayer.prepare()

        mediaPlayer.setOnCompletionListener {
            setIdle()
        }

        setPlaying()
        mediaPlayer.start()
    }

    override fun stop() {
        mediaPlayer.stop()
        setIdle()
    }

}
