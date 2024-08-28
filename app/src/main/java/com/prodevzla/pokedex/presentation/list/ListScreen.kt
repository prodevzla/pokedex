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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterOption
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
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

    CustomScaffold(
        modifier = modifier,
        title = "Pokedex",
        navIcon = {
            IconButton(onClick = onClickNavIcon) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        }
    ) {

        var showFilterDialog: Filter? by remember { mutableStateOf(null) }

        when (state) {
            ListState.Loading -> LoadingScreen()
            ListState.Error -> ErrorScreen()
            is ListState.Content -> {
                state.filters?.let { filters ->
                    FiltersRow(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                        filters = filters,
                        onClickFilter = {
                            showFilterDialog = it
                        },
                    )
                }

                Text(
                    text = "count: ${state.pokemonList.count()}",
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
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

                showFilterDialog?.let { filter ->
                    FilterBottomSheet(
                        filter = filter,
                        onDismiss = {
                            showFilterDialog = null
                        },
                        onClickItem = {
                            showFilterDialog = null
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
                types = emptyList(),
                generation = 1,
                gameVersions = emptyList()
            ),
            Pokemon(
                id = 6886,
                name = "Charmeleon",
                types = emptyList(),
                generation = 1,
                gameVersions = emptyList()
            ),
        ),
        filters = listOf(
            Filter(
                title = UiText.DynamicString("Select generation"),
                weight = 1.0f,
                selection = PokemonGeneration(
                    id = 1,
                    name = UiText.DynamicString("Gen I")
                ),
                values = emptyList(),
                onClickSelection = {},
                filterOption = FilterOption.GENERATION,
            ),
            Filter(
                title = UiText.DynamicString("Select type"),
                weight = 1.0f,
                selection = PokemonType(
                    id = 10,
                    name = UiText.DynamicString("Fire")
                ),
                values = emptyList(),
                onClickSelection = {},
                filterOption = FilterOption.TYPE,
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
