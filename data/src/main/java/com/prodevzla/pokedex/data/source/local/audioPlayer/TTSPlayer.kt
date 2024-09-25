package com.prodevzla.pokedex.data.source.local.audioPlayer

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener

class TTSPlayer(context: Context): Player() {

    private val tts: TextToSpeech = TextToSpeech(context) {}

    override fun play(audio: String) {
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                setPlaying()
            }

            override fun onDone(utteranceId: String?) {
                setIdle()
            }

            override fun onError(utteranceId: String?) {}

            override fun onStop(utteranceId: String?, interrupted: Boolean) {
                setIdle()

            }
        })

        tts.speak(audio, TextToSpeech.QUEUE_FLUSH, null, "utteranceId")

    }

    override fun stop() {
        tts.stop()
        setIdle()
    }

}
