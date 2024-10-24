@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package com.prodevzla.pokedex.presentation.ability

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.prodevzla.pokedex.presentation.ability.model.AbilityUiState
import com.prodevzla.pokedex.presentation.list.composable.PokemonCard
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.CardTitle
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable.InfoDetailText
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.PreviewData
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
    onDismiss: () -> Unit = {},
    onClickPokemon: (Pokemon) -> Unit = {},
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onEvent: (AbilityScreenEvent) -> Unit = remember(viewModel) {
        return@remember viewModel::onEvent
    }

    val skipPartiallyExpanded = remember {
        (state as? AbilityUiState.Content)?.sheetState == SheetValue.Expanded
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = { sheetValue ->
            onEvent.invoke(AbilityScreenEvent.SheetStateChange(sheetValue))
            true
        }
    )

    val lazyListState: LazyListState = rememberLazyListState()

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
//        containerColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = onDismiss,
    ) {

        if (state is AbilityUiState.Error) {
            ErrorScreen(modifier = Modifier.height(400.dp), tryAgain = {//todo: fix hardcoded height
                onEvent.invoke(AbilityScreenEvent.ClickTryAgain)
            })
            return@ModalBottomSheet
        }

        AbilityScreenContent(
            modifier = modifier,
            title = abilityName,
            isLoading = state is AbilityUiState.Loading,
            ability = (state as? AbilityUiState.Content)?.ability,
            pokemons = (state as? AbilityUiState.Content)?.pokemons,
            onEvent = { event ->
                when (event) {
                    is AbilityScreenEvent.OnClickPokemon -> onClickPokemon.invoke(event.pokemon)
                    else -> onEvent(event)
                }
            },
            lazyListState = lazyListState,
        )

    }
}

@Composable
fun AbilityScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean,
    ability: Ability?,
    pokemons: List<Pokemon>?,
    onEvent: (AbilityScreenEvent) -> Unit = {},
    lazyListState: LazyListState,
) {

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        item {

            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    //.align(Alignment.CenterHorizontally)
                    .wrapContentHeight(align = Alignment.CenterVertically),


                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(Modifier.height(MaterialTheme.spacing.medium))
        }

        item {
            CardTitle(text = R.string.ability_title_description)
        }

        item {
            ExpandableCard(isLoading = isLoading) {
                if (ability == null) {
                    return@ExpandableCard
                }
                InfoDetailText(text = ability.flavorText ?: "")
            }
        }

        item {
            CardTitle(text = R.string.ability_title_effect)
        }

        item {
            ExpandableCard(isLoading = isLoading) {
                if (ability == null) {
                    return@ExpandableCard
                }
                InfoDetailText(text = ability.shortEffect)
            }
        }

        item {
            CardTitle(text = R.string.ability_title_details)
        }

        item {
            ExpandableCard(isLoading = isLoading) {
                if (ability == null) {
                    return@ExpandableCard
                }
                InfoDetailText(text = ability.longEffect ?: "")
            }
        }

        item {
            CardTitle(text = R.string.ability_title_pokemon)
        }

        if (pokemons == null) {
            return@LazyColumn
        }

        items(pokemons, key = { it.id }) { item ->
            PokemonCard(
                pokemon = item,
                onClickItem = { pokemon ->
                    onEvent.invoke(AbilityScreenEvent.OnClickPokemon(pokemon))
                },
                onToggleSave = { pokemon ->
                    onEvent.invoke(AbilityScreenEvent.ToggleSave(pokemon))
                },
                sharedTransitionScope = null,
                animatedVisibilityScope = null,
            )
        }
    }
}

@ThemePreviews
@Composable
fun AbilityScreenPreview() {
    PokedexTheme {
        Surface {
            AbilityScreenContent(
                title = "Overgrow",
                isLoading = false,
                ability = PreviewData.ability1,
                pokemons = PreviewData.pokemonList,
                lazyListState = rememberLazyListState()
            )
        }
    }
}
