package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.usecase.PokemonInfoUI
import com.prodevzla.pokedex.presentation.pokemonDetail.GenericViewPagerErrorContent
import com.prodevzla.pokedex.presentation.pokemonDetail.PlayAudioContent
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun InfoScreen(
    viewModel: PokemonInfoViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    GenericViewPagerErrorContent(state is PokemonInfoUiState.Error) {
        InfoScreenContent(state = state)
    }
}

@Composable
fun InfoScreenContent(modifier: Modifier = Modifier, state: PokemonInfoUiState) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = MaterialTheme.spacing.medium)
    ) {

        Text(
            text = stringResource(R.string.tab_pokemon_info_species),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        SpeciesCard(state = state)
//        SpeciesCard(state = state)
//        SpeciesCard(state = state)
//        SpeciesCard(state = state)
    }
}

@Composable
fun SpeciesCard(modifier: Modifier = Modifier, state: PokemonInfoUiState) {
    ExpandableCard(modifier = modifier, isLoading = state is PokemonInfoUiState.Loading) {
        if (state is PokemonInfoUiState.Content) {

            InfoDetail(label = "") {
                WeightHeightText(text = state.content.pokemonInfo.flavorText)
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
                    WeightHeightText(text = state.content.height)
                }
                InfoDetail(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.tab_pokemon_info_weight)
                ) {
                    WeightHeightText(text = state.content.weight)
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
                    PlayAudioContent(state.content.pokemonInfo.flavorText)
                }
                InfoDetail(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.tab_pokemon_info_cry)
                ) {
                    PlayAudioContent(Uri.parse(state.content.pokemonInfo.cries))
                }

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
fun InfoScreenContentPreview() {
    PokedexTheme {
        Surface {
            InfoScreenContent(
                state = PokemonInfoUiState.Content(
                    content = PokemonInfoUI(
                        pokemonInfo = PokemonInfo(
                            height = 4733,
                            weight = 8327,
                            genderRate = 8498,
                            flavorText = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
                            cries = "https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/1.ogg"
                        ),
                        height = "erat",
                        weight = "definitionem"
                    )
                )
            )
        }
    }
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
