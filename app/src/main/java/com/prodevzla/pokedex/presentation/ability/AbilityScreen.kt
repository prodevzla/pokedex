@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package com.prodevzla.pokedex.presentation.ability

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.ability.model.AbilityUiState
import com.prodevzla.pokedex.presentation.list.PokemonList
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.CardTitle
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.InfoDetailText
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun AbilityScreen(
    modifier: Modifier = Modifier,
    abilityId: Int,
    abilityName: String,
    viewModel: AbilityViewModel = hiltViewModel(
        key = "ability$abilityId",
        creationCallback = { factory: AbilityViewModel.MyViewModelFactory ->
            factory.create(abilityId)
        }
    ),
    onDismiss: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ModalBottomSheet(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = onDismiss,
    ) {

        if (state is AbilityUiState.Error) {
            ErrorScreen(tryAgain = {})
            return@ModalBottomSheet
        }

        AbilityScreenContent(
            modifier = modifier,
            title = abilityName,
            isLoading = state is AbilityUiState.Loading,
            ability = (state as? AbilityUiState.Content)?.ability,
            pokemons = (state as? AbilityUiState.Content)?.pokemons,
        )

    }
}

@Composable
fun AbilityScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean,
    ability: Ability?,
    pokemons: List<Pokemon>?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MaterialTheme.colorScheme.onSurface)
                .align(Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically),


            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(MaterialTheme.spacing.medium))


        CardTitle(text = R.string.ability_title_description)

        ExpandableCard(isLoading = isLoading) {
            if (ability == null) {
                return@ExpandableCard
            }
            InfoDetailText(text = ability.flavorText)
        }

        CardTitle(text = R.string.ability_title_effect)

        ExpandableCard(isLoading = isLoading) {
            if (ability == null) {
                return@ExpandableCard
            }
            InfoDetailText(text = ability.shortEffect)
        }

        CardTitle(text = R.string.ability_title_details)

        ExpandableCard(isLoading = isLoading) {
            if (ability == null) {
                return@ExpandableCard
            }
            InfoDetailText(text = ability.longEffect)
        }

        CardTitle(text = R.string.ability_title_pokemon)

        val lazyListState = rememberLazyListState()

        if (pokemons == null) {
            return@Column
        }
        PokemonList(
            lazyListState = lazyListState,
            items = pokemons,
            sharedTransitionScope = null,
            animatedVisibilityScope = null,
            onClickPokemon = {

            },
            onClickSave = {

            }
        )
    }


}

@ThemePreviews
@Composable
fun AbilityScreenPreview() {
    PokedexTheme {
        Surface {
            AbilityScreenContent(
                isLoading = false,
                title = "Overgrow",
                ability = Ability(
                    id = 6260,
                    name = "Overgrow",
                    flavorText = "Overgrow",
                    shortEffect = "Powers up Grass-type moves when the Pokemon's HP is low",
                    longEffect = "Powers up Grass-type moves when the Pokemon's HP is low asdasd asd"
                ),
                pokemons = listOf(
                    Pokemon(
                        id = 4,
                        name = "Charmander",
                        types = listOf(
                            PokemonType(
                                id = 10,
                                name = UiText.DynamicString("Fire")
                            )
                        ),
                        generation = 1,
                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
                        isSaved = false,
                        abilities = listOf(1,2)
                        //gameVersions = emptyList()
                    ),
                    Pokemon(
                        id = 5,
                        name = "Charizard",
                        types = listOf(
                            PokemonType(
                                id = 10,
                                name = UiText.DynamicString("Fire")
                            )
                        ),
                        generation = 1,
                        image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png",
                        isSaved = true,
                        abilities = listOf(1,2)
                        //gameVersions = emptyList()
                    ),
                )
            )
        }
    }
}