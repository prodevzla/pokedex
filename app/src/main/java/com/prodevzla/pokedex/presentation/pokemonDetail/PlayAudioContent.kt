package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.WeightHeightText
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun PlayAudioContent(
    state: AudioPlaybackState,
    togglePlay: () -> Unit,
) {
    SubcomposeLayout { constraints ->
        val textPlaceable = subcompose("Text") {
            WeightHeightText(text = "text")
        }[0].measure(constraints)

        val textHeightDp = textPlaceable.height.toDp()

        val iconButtonPlaceable = subcompose("IconButton") {
            IconButton(
                onClick = togglePlay,
                modifier = Modifier
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

@ThemePreviews
@Composable
fun PlayAudioContentIdlePreview() {
    PokedexTheme {
        Surface {
            PlayAudioContent(state = AudioPlaybackState.IDLE) { }
        }
    }
}

@ThemePreviews
@Composable
fun PlayAudioContentPlayingPreview() {
    PokedexTheme {
        Surface {
            PlayAudioContent(state = AudioPlaybackState.PLAYING) { }
        }
    }
}
