package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.WeightHeightText

//@Composable
//fun PlayAudioContent(uri: Uri) {
//    if (LocalInspectionMode.current) {
//        PlayAudioContent(state = AudioPlay.IDLE, onClickPlay = {})
//        return
//    }
//
//    var audioPlayState by remember { mutableStateOf(AudioPlay.IDLE) }
//
//    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
//
//    val context = LocalContext.current
//    DisposableEffect(context) {
//        mediaPlayer = MediaPlayer().apply {
//            setDataSource(context, uri)
//
//            // Use prepareAsync to avoid blocking the UI thread
//            prepareAsync()
//
//            // Listener for when preparation is complete
//            setOnPreparedListener {
//                // Media is prepared, can update the UI if needed
//            }
//
//            // Listener for when playback is complete
//            setOnCompletionListener {
//                audioPlayState = AudioPlay.IDLE
//            }
//        }
//
//        // Dispose the MediaPlayer when the composable leaves the composition
//        onDispose {
//            mediaPlayer?.release()
//        }
//    }
//
//    PlayAudioContent(
//        state = audioPlayState,
//        onClickPlay = {
//            audioPlayState = AudioPlay.PLAYING
//            mediaPlayer?.start()
//        },
//        onClickStop = {
//            audioPlayState = AudioPlay.IDLE
//            mediaPlayer?.stop()
//            mediaPlayer?.prepareAsync()
//        }
//    )
//}

@Composable
fun PlayAudioContent(
    state: AudioPlaybackState,
    onClickPlay: () -> Unit,
    onClickStop: () -> Unit
) {
    if (LocalInspectionMode.current) {
        PlayAudioContent(state = AudioPlaybackState.IDLE, onClickPlay = {}, onClickStop = {})
        return
    }

    PlayAudioContent(
        state = state,
        onClickPlay = {
            onClickPlay.invoke()
        },
        onClickStop = {
            onClickStop.invoke()
        },
        flag = false
    )
}

@Composable
fun PlayAudioContent(state: AudioPlaybackState, onClickPlay: () -> Unit = {}, onClickStop: () -> Unit = {}, flag: Boolean) { //TODO remove flag

    SubcomposeLayout { constraints ->
        val textPlaceable = subcompose("Text") {
            WeightHeightText(text = "text")
        }[0].measure(constraints)

        val textHeightDp = textPlaceable.height.toDp()

        val iconButtonPlaceable = subcompose("IconButton") {
            IconButton(
                onClick = when (state) {
                    AudioPlaybackState.IDLE -> onClickPlay
                    AudioPlaybackState.PLAYING -> onClickStop
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(textHeightDp)
            ) {
                when (state) {
                    AudioPlaybackState.IDLE -> Icon(
                        Icons.Rounded.PlayArrow,
                        contentDescription = "play"
                    )

                    AudioPlaybackState.PLAYING -> Icon(
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

