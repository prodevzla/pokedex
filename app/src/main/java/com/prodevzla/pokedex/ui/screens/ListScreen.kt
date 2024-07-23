package com.prodevzla.pokedex.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.model.Pokemon
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.util.CustomScaffold
import com.prodevzla.pokedex.viewModel.ListViewModel

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

    StateLessListScreen(
        state = state,
        context = context,
        lazyGridState = lazyGridState,
        focusRequester = focusRequester,
        onSearchChange = viewModel::onSearchChange
    )
}

@Composable
fun StateLessListScreen(
    state: ListViewModel.ListUIState,
    context: Context,
    lazyGridState: LazyGridState,
    focusRequester: FocusRequester,
    onSearchChange: (String) -> Unit = {},
) {
    CustomScaffold(title = "Pokedex") {
        PokemonList(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            context = context,
            state = state,
            lazyGridState = lazyGridState,
            focusRequester = focusRequester,
            onSearchChange = onSearchChange
        )
    }
}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    context: Context,
    state: ListViewModel.ListUIState,
    lazyGridState: LazyGridState,
    focusRequester: FocusRequester,
    onSearchChange: (String) -> Unit,
) {

    Spacer(modifier = Modifier.height(16.dp))

    Image(
        modifier = modifier
            .height(100.dp),
        painter = painterResource(id = R.drawable.pokemon),
        contentDescription = "pokemon",
    )

    Spacer(modifier = Modifier.height(16.dp))

    SearchBar(
        modifier = modifier.fillMaxWidth(),
        focusRequester = focusRequester,
        state = state,
        onSearchChange = onSearchChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }

        return
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        items(state.pokemonList) { item ->
            PokemonCard(context = context, pokemon = item)
        }



        item(span = { GridItemSpan(2) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )

            }

        }

    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    state: ListViewModel.ListUIState,
    onSearchChange: (String) -> Unit,
) {
    val shape = RoundedCornerShape(
        topStartPercent = 50,
        topEndPercent = 5,
        bottomEndPercent = 50,
        bottomStartPercent = 5
    )

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .padding(horizontal = 16.dp)
            .background(
                color = Color.Blue,
                shape = shape,
            ),
        value = state.search,
        shape = shape,
        onValueChange = onSearchChange,
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = Color.White,
        ),
        colors = OutlinedTextFieldDefaults.colors().copy(
            cursorColor = Color.White
        )
    )
}

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    context: Context,
    pokemon: Pokemon,
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .padding(8.dp),//distance between cards
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(pokemon.image.toString())
                    .placeholder(R.drawable.pokemon)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(40.dp)
                    .offset(y = (-8).dp)
            )

            Text(
                text = pokemon.name,

                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)

            )
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    PokedexTheme {
        StateLessListScreen(
            state = ListViewModel.ListUIState(
                isLoading = false,
                pokemonList = listOf(
                    Pokemon(
                        id = 6885,
                        name = "Charmander",
                    )
                ),
                search = "Charmander"
            ),
            context = LocalContext.current,
            lazyGridState = LazyGridState(),
            focusRequester = FocusRequester(),
        )
    }
}

@Preview
@Composable
fun ListScreenLoadingPreview() {
    PokedexTheme {
        StateLessListScreen(
            state = ListViewModel.ListUIState(
                isLoading = true,
                pokemonList = listOf(),
                search = "Charmander"
            ),
            context = LocalContext.current,
            lazyGridState = LazyGridState(),
            focusRequester = FocusRequester(),
        )
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokedexTheme {
        PokemonCard(
            pokemon = Pokemon(
                4,
                "Charmander"
            ),
            context = LocalContext.current,
        )
    }
}
