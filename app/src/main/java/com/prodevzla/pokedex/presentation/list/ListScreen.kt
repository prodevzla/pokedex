package com.prodevzla.pokedex.presentation.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing
import com.prodevzla.pokedex.ui.utils.ErrorScreen
import com.prodevzla.pokedex.ui.utils.LoadingScreen
import com.prodevzla.pokedex.ui.utils.ThemePreviews

@Composable
fun ListScreen(viewModel: ListViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val state by viewModel.uiState.collectAsState()

    val focusRequester = remember { FocusRequester() }

    val lazyGridState = rememberLazyGridState()

    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = lazyGridState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItem = visibleItems.lastOrNull()
            totalItemsCount > 0 && lastVisibleItem != null && lastVisibleItem.index == totalItemsCount - 1
        }
    }

    LaunchedEffect(isAtBottom) {
        if (isAtBottom) {
            viewModel.loadMorePokemon()
        }
    }

    val onSearchChange: (String) -> Unit = remember(viewModel) {
        return@remember viewModel::onSearchChange
    }

    val onClickPokemon: (Pokemon) -> Unit = {
        Toast.makeText(context, "Pokemon is ${it.name}", Toast.LENGTH_SHORT).show()
    }

    ListContent(
        state = state,
        context = context,
        lazyGridState = lazyGridState,
        focusRequester = focusRequester,
        onSearchChange = onSearchChange,
        onClickPokemon = onClickPokemon,
    )

}

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListState,
    context: Context,
    lazyGridState: LazyGridState,
    focusRequester: FocusRequester,
    onSearchChange: (String) -> Unit = {},
    onClickPokemon: (Pokemon) -> Unit = {},
) {
    CustomScaffold(modifier = modifier, title = "Pokedex") {

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        Image(
            modifier = modifier
                .height(100.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.pokemon),
            contentDescription = "pokemon",
        )

        when (state) {
            ListState.Loading -> LoadingScreen()
            ListState.Error -> ErrorScreen()
            is ListState.Content -> {

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                SearchBar(
                    modifier = modifier.fillMaxWidth(),
                    focusRequester = focusRequester,
                    search = state.search,
                    onSearchChange = onSearchChange,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                PokemonList(
                    context = context,
                    items = state.data,
                    lazyGridState = lazyGridState,
                    onClickPokemon = onClickPokemon,
                )
            }
        }
    }
}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    context: Context,
    items: List<Pokemon>,
    lazyGridState: LazyGridState,
    onClickPokemon: (Pokemon) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(MaterialTheme.spacing.small),
    ) {
        items(items, key = { it.id }) { item ->
            PokemonCard(context = context, pokemon = item, onClickPokemon = onClickPokemon)
        }

        item(span = { GridItemSpan(2) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.medium),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun ListScreenPreview() {
    val state = ListState.Content(
        data = mutableListOf(
            Pokemon(
                id = 6885,
                name = "Charmander",
            ),
            Pokemon(
                id = 6886,
                name = "Charmeleon",
            ),
        ),
        search = "Charmander",
    )
    PokedexTheme {
        ListContent(
            state = state,
            context = LocalContext.current,
            lazyGridState = LazyGridState(),
            focusRequester = FocusRequester()
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
            lazyGridState = LazyGridState(),
            focusRequester = FocusRequester()
        )
    }
}
