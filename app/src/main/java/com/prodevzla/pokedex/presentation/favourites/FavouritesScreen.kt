@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.prodevzla.pokedex.presentation.favourites

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.presentation.favourites.model.FavouritesState
import com.prodevzla.pokedex.presentation.list.composable.PokemonCard
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.LoadingScreen
import com.prodevzla.pokedex.presentation.util.PreviewData
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel = hiltViewModel(),
    onClickPokemon: (Pokemon) -> Unit,
    onClickBack: () -> Unit,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onEvent: (FavouritesScreenEvent) -> Unit = remember(viewModel) {
        return@remember viewModel::onEvent
    }

    FavouritesScreenContent(
        state = state,
        onEvent = { event: FavouritesScreenEvent ->
            when (event) {
                FavouritesScreenEvent.ClickBack -> onClickBack.invoke()
                is FavouritesScreenEvent.ClickPokemon -> onClickPokemon.invoke(event.pokemon)
                is FavouritesScreenEvent.ToggleSave -> onEvent(event)
            }
        }
    )
}

@Composable
fun FavouritesScreenContent(
    state: FavouritesState,
    onEvent: (FavouritesScreenEvent) -> Unit,
) {
    CustomScaffold(
        title = {
            Text(
                text = stringResource(R.string.title_favourites),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        showTitleDivider = true,
        navIcon = {
            IconButton(onClick = {
                onEvent(FavouritesScreenEvent.ClickBack)
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back")
            }
        },
    ) {
        when (state) {
            FavouritesState.Loading -> LoadingScreen()

            FavouritesState.Error -> ErrorScreen()

            is FavouritesState.Content -> {

                if (state.pokemonList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("you gotta like some pokemons")
                    }
                    return@CustomScaffold
                }

                LazyColumn(
                    contentPadding = PaddingValues(MaterialTheme.spacing.small),
                ) {
                    items(state.pokemonList, key = { it.id }) { item ->

                        PokemonCard(
                            pokemon = item,
                            onClickItem = { pokemon ->
                                onEvent.invoke(FavouritesScreenEvent.ClickPokemon(pokemon))
                            },
                            onToggleSave = { pokemon ->
                                onEvent.invoke(FavouritesScreenEvent.ToggleSave(pokemon))
                            },
                            sharedTransitionScope = null,
                            animatedVisibilityScope = null,
                        )
                    }
                }

            }
        }

    }
}

@Composable
@Preview
fun FavouritesScreenPreview() {
    PokedexTheme {
        FavouritesScreenContent(
            state = FavouritesState.Content(
                pokemonList = PreviewData.pokemonList
            ),
            onEvent = {}
        )
    }
}
