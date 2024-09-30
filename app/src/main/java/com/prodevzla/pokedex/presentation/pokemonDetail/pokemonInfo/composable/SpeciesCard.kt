package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonSpec
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.asString
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun SpeciesCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    state: PokemonSpec?,
    onToggleVoiceover: (String) -> Unit = {},
    onToggleCry: (String) -> Unit = {},
) {
    ExpandableCard(modifier = modifier, isLoading = isLoading) {
        if (state == null) {
            return@ExpandableCard
        }

        InfoDetail(label = "") {
            WeightHeightText(text = state.flavorText)
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {

            InfoDetail(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tab_pokemon_info_height)
            ) {
                WeightHeightText(text = state.height.asString())
            }
            InfoDetail(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tab_pokemon_info_weight)
            ) {
                WeightHeightText(text = state.weight.asString())
            }

        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {

            InfoDetail(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tab_pokemon_info_voiceover)
            ) {
                PlayAudioContent(
                    state = state.statePlayVoiceover,
                    togglePlay = {
                        onToggleVoiceover.invoke(state.flavorText)
                    }
                )
            }
            InfoDetail(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.tab_pokemon_info_cry)
            ) {
                PlayAudioContent(
                    state = state.statePlayCry,
                    togglePlay = {
                        onToggleCry.invoke(state.cry)
                    }
                )
            }
        }

    }
}


@Composable
fun InfoDetail(modifier: Modifier = Modifier, label: String, content: @Composable () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(MaterialTheme.spacing.small)
                )
        ) {
            content()
        }

        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@ThemePreviews
@Composable
fun SpeciesCardPreview() {
    PokedexTheme {
        Surface {
            SpeciesCard(
                isLoading = false,
                state = PokemonSpec(
                    height = UiText.DynamicString("120 cm"),
                    weight = UiText.DynamicString("30 Kg"),
                    //genderRate = 8498,
                    flavorText = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
                    cry = "https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/1.ogg",
                    statePlayVoiceover = AudioPlaybackState.PLAYING,
                    statePlayCry = AudioPlaybackState.IDLE
                ),
            )
        }
    }
}

@Composable
fun WeightHeightText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}

@ThemePreviews
@Composable
fun InfoDetailPreview() {
    PokedexTheme {
        Surface {
            InfoDetail(label = "height") {
                WeightHeightText(text = "0.1m")
            }
        }
    }
}
