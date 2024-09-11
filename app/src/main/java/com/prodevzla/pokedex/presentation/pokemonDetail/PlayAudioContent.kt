package com.prodevzla.pokedex.presentation.pokemonDetail

import android.media.MediaPlayer
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import java.util.Locale

@Composable
fun PlayAudioContent(uri: Uri) {
    if (LocalInspectionMode.current) {
        PlayAudioContent(state = AudioPlay.IDLE, onClickPlay = {})
        return
    }

    var audioPlayState by remember { mutableStateOf(AudioPlay.IDLE) }

    val mediaPlayer = MediaPlayer().apply {
        setDataSource(LocalContext.current, uri)
        prepare()
        setOnCompletionListener {
            audioPlayState = AudioPlay.IDLE
        }
    }

    PlayAudioContent(
        state = audioPlayState,
        onClickPlay = {
            audioPlayState = AudioPlay.PLAYING
            mediaPlayer.start()
        },
        onClickStop = {
            mediaPlayer.stop()
        }
    )
}

@Composable
fun PlayAudioContent(value: String) {
    if (LocalInspectionMode.current) {
        PlayAudioContent(state = AudioPlay.IDLE, onClickPlay = {})
        return
    }

    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    var audioPlayState by remember { mutableStateOf(AudioPlay.IDLE) }

    DisposableEffect(context) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US

                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        audioPlayState = AudioPlay.PLAYING
                    }

                    override fun onDone(utteranceId: String?) {
                        audioPlayState = AudioPlay.IDLE
                    }

                    override fun onError(utteranceId: String?) {}

                    override fun onStop(utteranceId: String?, interrupted: Boolean) {
                        audioPlayState = AudioPlay.IDLE
                    }

                })
            }

        }
        onDispose {
            tts?.shutdown()
        }

    }

    PlayAudioContent(
        state = audioPlayState,
        onClickPlay = {
            tts?.speak(value, TextToSpeech.QUEUE_FLUSH, null, "utteranceId")
        },
        onClickStop = {
            tts?.stop()
        }
    )
}

@Composable
fun PlayAudioContent(state: AudioPlay, onClickPlay: () -> Unit = {}, onClickStop: () -> Unit = {}) {

    SubcomposeLayout { constraints ->
        val textPlaceable = subcompose("Text") {
            WeightHeightText(text = "text")
        }[0].measure(constraints)

        val textHeightDp = textPlaceable.height.toDp()

        val iconButtonPlaceable = subcompose("IconButton") {
            IconButton(
                onClick = when (state) {
                    AudioPlay.IDLE -> onClickPlay
                    AudioPlay.PLAYING -> onClickStop
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(textHeightDp)
            ) {
                when (state) {
                    AudioPlay.IDLE -> Icon(Icons.Default.PlayArrow, contentDescription = "play")
                    AudioPlay.PLAYING -> Icon(
                        Icons.Filled.ShoppingCart, contentDescription = "stop"
                    )
                }

            }
        }[0].measure(constraints)

        layout(iconButtonPlaceable.width, iconButtonPlaceable.height) {
            iconButtonPlaceable.place(0, 0)
        }
    }

}

enum class AudioPlay {
    IDLE, PLAYING,
}
