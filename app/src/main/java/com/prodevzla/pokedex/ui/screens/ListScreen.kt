package com.prodevzla.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

    val state by viewModel.uiState.collectAsState()

    val focusRequester = remember { FocusRequester() }

    CustomScaffold(title = "Pokedex") {
        PokemonList(
            state = state,
            focusRequester = focusRequester,
            onSearchChange = viewModel::onSearchChange
        )
    }
}

@Composable
fun ColumnScope.PokemonList(
    state: ListViewModel.ListUIState,
    focusRequester: FocusRequester,
    onSearchChange: (String) -> Unit,
) {

    Spacer(modifier = Modifier.height(16.dp))

    Image(
        modifier = Modifier
            .height(100.dp)
            .align(Alignment.CenterHorizontally),
        painter = painterResource(id = R.drawable.pokemon),
        contentDescription = "pokemon",
    )

    Spacer(modifier = Modifier.height(16.dp))

    val shape = RoundedCornerShape(
        topStartPercent = 50,
        topEndPercent = 5,
        bottomEndPercent = 50,
        bottomStartPercent = 5
    )

    OutlinedTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .align(Alignment.CenterHorizontally)
            .background(
                color = Color.Blue,
                shape = shape
            ),
        value = state.search,
        shape = shape,
        onValueChange = onSearchChange,
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = Color.White
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        items(state.pokemonList) { item ->
            PokemonCard(pokemon = item)
        }

    }
}

@Composable
fun PokemonCard(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Card(
        modifier = modifier
            .height(100.dp)
            .padding(8.dp),//distance between cards
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.image.toString())
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
        ListScreen()
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
            )
        )
    }
}
