package com.prodevzla.pokedex.presentation.list

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.domain.Filter
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.PokemonType
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.LoadingScreen
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    onClickNavIcon: () -> Unit,
    onClickPokemon: (Int) -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.uiState.collectAsState()

    ListContent(
        state = state,
        context = context,
        onClickNavIcon = onClickNavIcon,
        onClickPokemon = onClickPokemon,
    )

}
//TODO use events pattern
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListState,
    context: Context,
    onClickNavIcon: () -> Unit = {},
    onClickPokemon: (Int) -> Unit = {},
) {
    var showGenerations by remember { mutableStateOf(false) }
    var showTypes by remember { mutableStateOf(false) }

    CustomScaffold(
        modifier = modifier,
        title = "Pokedex",
        navIcon = {
            IconButton(onClick = onClickNavIcon) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        }
    ) {
        when (state) {
            ListState.Loading -> LoadingScreen()
            ListState.Error -> ErrorScreen()
            is ListState.Content -> {
                FiltersRow(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    filters = state.filters,
                    onClickFilter = {
                        when(it) {
//                            is Filter.Version -> TODO()
                            is Filter.Generation -> showGenerations = true
                            is Filter.Type -> showTypes = true
                        }
                    },
                )

                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(MaterialTheme.spacing.small),
                ) {
                    items(state.pokemonList, key = { it.id }) { item ->
                        PokemonCard(context = context, pokemon = item, onClickPokemon = onClickPokemon)
                    }
                }

                if (showTypes) {
                    FilterBottomSheet(
                        items = state.filters[1].values,//TODO improve this
                        onDismiss = { showTypes = false },
                        onClickType = {
                            state.filters[1].onClickSelection.invoke(it)
                            showTypes = false
                        }
                    )
                }

                if (showGenerations) {
                    FilterBottomSheet(
                        items = state.filters[0].values,//TODO improve this
                        onDismiss = { showGenerations = false },
                        onClickType = {
                            state.filters[0].onClickSelection.invoke(it)
                            showGenerations = false
                        }
                    )
                }
            }
        }
    }

}

@ThemePreviews
@Composable
fun ListScreenPreview() {
    val state = ListState.Content(
        pokemonList = mutableListOf(
            Pokemon(
                id = 6885,
                name = "Charmander",
                types = listOf(),
                generation = 1,
            ),
            Pokemon(
                id = 6886,
                name = "Charmeleon",
                types = listOf(),
                generation = 1,
            ),
        ),
        filters = listOf(
//            Filter.Version(
//                weight = 1.5f,
//            ),
            Filter.Generation(
                weight = 1.0f,
                selection = PokemonGeneration(
                    id = 1,
                    name = "Gen I"
                ),
                values = emptyList(),
                onClickSelection = {}
            ),
            Filter.Type(
                weight = 1.0f,
                selection = PokemonType(
                    id = 10,
                    name = "Fire"
                ),
                values = emptyList(),
                onClickSelection = {}
            )
        ),
    )
    PokedexTheme {
        ListContent(
            state = state,
            context = LocalContext.current,
        )
    }
}

@ThemePreviews
@Composable
fun ListScreenLoadingPreview() {
    val state = ListState.Loading
    PokedexTheme {
        ListContent(
            state = state,
            context = LocalContext.current,
        )
    }
}
