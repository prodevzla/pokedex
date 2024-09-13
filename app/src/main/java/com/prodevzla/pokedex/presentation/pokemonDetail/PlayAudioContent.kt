package com.prodevzla.pokedex.presentation.pokemonDetail

import android.media.MediaPlayer
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
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
import androidx.compose.ui.res.painterResource
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.WeightHeightText

@Composable
fun PlayAudioContent(uri: Uri) {
    if (LocalInspectionMode.current) {
        PlayAudioContent(state = AudioPlay.IDLE, onClickPlay = {})
        return
    }

    var audioPlayState by remember { mutableStateOf(AudioPlay.IDLE) }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val context = LocalContext.current
    DisposableEffect(context) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, uri)

            // Use prepareAsync to avoid blocking the UI thread
            prepareAsync()

            // Listener for when preparation is complete
            setOnPreparedListener {
                // Media is prepared, can update the UI if needed
            }

            // Listener for when playback is complete
            setOnCompletionListener {
                audioPlayState = AudioPlay.IDLE
            }
        }

        // Dispose the MediaPlayer when the composable leaves the composition
        onDispose {
            mediaPlayer?.release()
        }
    }

    PlayAudioContent(
        state = audioPlayState,
        onClickPlay = {
            audioPlayState = AudioPlay.PLAYING
            mediaPlayer?.start()
        },
        onClickStop = {
            audioPlayState = AudioPlay.IDLE
            mediaPlayer?.stop()
            mediaPlayer?.prepareAsync()
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
        if (tts == null) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                //tts?.language = Locale.US

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
                    AudioPlay.IDLE -> Icon(Icons.Rounded.PlayArrow, contentDescription = "play")
                    AudioPlay.PLAYING -> Icon(
                        painterResource(R.drawable.ic_stop), contentDescription = "stop"
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

