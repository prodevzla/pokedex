package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.ability.AbilityScreen
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.AbilitiesCard
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.CardTitle
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.SpeciesCard
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonInfoUiState
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonSpec
import com.prodevzla.pokedex.presentation.util.ErrorScreen
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

    when (state) {
        PokemonInfoUiState.Error -> ErrorScreen(tryAgain = {
            onEvent(PokemonInfoEvent.ClickTryAgain)
        })

        else -> InfoScreenContent(
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
    var showAbilityDialog: Ability? by remember { mutableStateOf(null) }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = MaterialTheme.spacing.medium)
    ) {

        CardTitle(text = R.string.tab_pokemon_info_species)

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

        CardTitle(text = R.string.tab_pokemon_info_abilities)

        AbilitiesCard(
            isLoading = state is PokemonInfoUiState.Loading,
            abilities = (state as? PokemonInfoUiState.Content)?.abilities,
            pokemonType = (state as? PokemonInfoUiState.Content)?.pokemonType,
            onClickAbility = {
                onEvent.invoke(PokemonInfoEvent.OnClickAbility(it))
                showAbilityDialog = it
            }
        )

        showAbilityDialog?.let {
            AbilityScreen(
                ability = it,
                onDismiss = {
                    showAbilityDialog = null
                }
            )
        }
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
                        height = UiText.DynamicString("120 cm"),
                        weight = UiText.DynamicString("30 Kg"),
                        //genderRate = 8498,
                        flavorText = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
                        cry = "https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/1.ogg",
                        statePlayVoiceover = AudioPlaybackState.IDLE,
                        statePlayCry = AudioPlaybackState.IDLE
                    ),
                    abilities = listOf(
                        Ability(
                            name = "Run away",
                            description = "Enables a sure getaway from wild Pokemon",
                            isHidden = false
                        ),
                        Ability(
                            name = "Hustle",
                            description = "Boosts the attack stat, but lowers accuracy",
                            isHidden = true
                        )
                    ),
                    pokemonType = PokemonType(
                        id = 1,
                        name = UiText.DynamicString("Normal")
                    )
                )
            )
        }
    }
}
