package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.ability.AbilityScreen
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.AbilitiesCard
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.CardTitle
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.SpeciesCard
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model.PokemonInfoUiState
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.PreviewData
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun InfoScreen(
    viewModel: PokemonInfoViewModel = hiltViewModel(),
    onClickPokemon: (Pokemon) -> Unit = {},
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
            onEvent = onEvent,
            onClickPokemon = onClickPokemon,
        )
    }

}

@Composable
fun InfoScreenContent(
    modifier: Modifier = Modifier,
    state: PokemonInfoUiState,
    onEvent: (PokemonInfoEvent) -> Unit = {},
    onClickPokemon: (Pokemon) -> Unit = {},
) {

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
            }
        )

        (state as? PokemonInfoUiState.Content)?.showAbilityDialog?.let {
            AbilityScreen(
                abilityId = it.id,
                abilityName = it.name,
                onDismiss = {
                    onEvent.invoke(PokemonInfoEvent.DismissAbilityDialog)
                },
                onClickPokemon = onClickPokemon
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
        InfoScreenContent(
            state = PokemonInfoUiState.Content(
                spec = PreviewData.pokemonSpec,
                abilities = PreviewData.pokemonAbilityList,
                pokemonType = PokemonType(
                    id = 1,
                    name = UiText.DynamicString("Normal")
                ),
                showAbilityDialog = null
            )
        )
    }
}
