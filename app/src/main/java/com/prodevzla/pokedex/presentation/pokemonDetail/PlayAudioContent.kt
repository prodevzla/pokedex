package com.prodevzla.pokedex.presentation.pokemonDetail

import android.media.MediaPlayer
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
        PlayAudioContent(onClickPlay = {})
        return
    }

    val mediaPlayer = MediaPlayer.create(LocalContext.current, uri)
    PlayAudioContent {
        mediaPlayer.start()
    }
}

@Composable
fun PlayAudioContent(value: String) {
    if (LocalInspectionMode.current) {
        PlayAudioContent(onClickPlay = {})
        return
    }

    val context = LocalContext.current
    var tts: TextToSpeech? by remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
            }
        }
        onDispose {
            tts?.shutdown()
        }
    }

    PlayAudioContent {
        tts?.speak(value, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}

@Composable
fun PlayAudioContent(onClickPlay: () -> Unit) {

    SubcomposeLayout { constraints ->
        val textPlaceable = subcompose("Text") {
            WeightHeightText(text = "text")
        }[0].measure(constraints)

        val textHeightDp = textPlaceable.height.toDp()

        val iconButtonPlaceable = subcompose("IconButton") {
            IconButton(
                onClick = onClickPlay,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(textHeightDp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "play")
            }
        }[0].measure(constraints)

        layout(iconButtonPlaceable.width, iconButtonPlaceable.height) {
            iconButtonPlaceable.place(0, 0)
        }
    }

}

