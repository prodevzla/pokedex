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
import com.prodevzla.pokedex.model.domain.Pokemon
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

    val onClickType: (Int) -> Unit = remember(viewModel) {
        return@remember viewModel::onClickType
    }

    ListContent(
        state = state,
        context = context,
        onClickNavIcon = onClickNavIcon,
        onClickPokemon = onClickPokemon,
        onClickType = onClickType,
    )

}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListState,
    context: Context,
    onClickNavIcon: () -> Unit = {},
    onClickPokemon: (Int) -> Unit = {},
    onClickType: (Int) -> Unit = {},
) {
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
                    onClickFilterType = {
                        showTypes = true
                    },
                )

                PokemonList(
                    context = context,
                    items = state.pokemonList,
                    onClickPokemon = onClickPokemon,
                )

                if (showTypes) {
                    FilterTypeBottomSheet(
                        pokemonTypes = state.pokemonTypes,
                        onDismiss = { showTypes = false },
                        onClickType = {
                            onClickType.invoke(it)
                            showTypes = false
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    context: Context,
    items: List<Pokemon>,
    onClickPokemon: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(MaterialTheme.spacing.small),
    ) {
        items(items, key = { it.id }) { item ->
            PokemonCard(context = context, pokemon = item, onClickPokemon = onClickPokemon)
        }

//        item(span = { GridItemSpan(2) }) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = MaterialTheme.spacing.medium),
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center),
//                    color = MaterialTheme.colorScheme.surface,
//                )
//            }
//        }
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
                versions = listOf(),
            ),
            Pokemon(
                id = 6886,
                name = "Charmeleon",
                types = listOf(),
                versions = listOf(),
            ),
        ),
        pokemonTypes = emptyList(),
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
