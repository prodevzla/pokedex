package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.presentation.pokemonDetail.GenericViewPagerErrorContent
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun InfoScreen(
    viewModel: PokemonInfoViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onEvent: (PokemonInfoEvent) -> Unit = remember(viewModel) {
        return@remember viewModel::onEvent
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        onEvent(PokemonInfoEvent.ScreenStopped)
    }

    GenericViewPagerErrorContent(state is PokemonInfoUiState.Error) {
        InfoScreenContent(
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
fun InfoScreenContent(
    modifier: Modifier = Modifier,
    state: PokemonInfoUiState,
    onEvent: (PokemonInfoEvent) -> Unit = {},
) {
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

        SpeciesCard(
            isLoading = state is PokemonInfoUiState.Loading,
            state = (state as? PokemonInfoUiState.Content)?.spec,
            onToggleVoiceover = {
                onEvent.invoke(PokemonInfoEvent.TogglePlayVoiceover(it))
            },
            onToggleCry = {
                onEvent.invoke(PokemonInfoEvent.TogglePlayCry(it))
            },
        )

        //todo create composable for this following Text as it will be reused across the 4 tabs
        Text(
            text = stringResource(R.string.tab_pokemon_info_abilities),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )

        AbilitiesCard(
            isLoading = state is PokemonInfoUiState.Loading,
            abilities = (state as? PokemonInfoUiState.Content)?.abilities
        )
//        SpeciesCard(state = state)
//        SpeciesCard(state = state)
//        SpeciesCard(state = state)
    }
}


@ThemePreviews
@Composable
fun InfoScreenContentPreview() {
    PokedexTheme {
        Surface {
            InfoScreenContent(
                state = PokemonInfoUiState.Content(
                    spec = PokemonSpec(
                        height = "120 cm",
                        weight = "30 Kg",
                        genderRate = 8498,
                        flavorText = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
                        cry = "https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/1.ogg",
                        statePlayVoiceover = AudioPlaybackState.IDLE,
                        statePlayCry = AudioPlaybackState.IDLE
                    ),
                    abilities = listOf("ability1")
                )
            )
        }
    }
}
