package com.prodevzla.pokedex.presentation.list

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing
import com.prodevzla.pokedex.presentation.util.ThemePreviews

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    context: Context,
    pokemon: Pokemon,
    onClickPokemon: (Int) -> Unit = {},
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .padding(MaterialTheme.spacing.small), // distance between cards
        shape = RoundedCornerShape(MaterialTheme.spacing.small),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.surface

        ),
        onClick = {
            onClickPokemon.invoke(pokemon.id)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model =
                ImageRequest
                    .Builder(context)
                    .data(pokemon.image.toString())
                    .placeholder(R.drawable.pokemon)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(40.dp)
                    .offset(y = -MaterialTheme.spacing.small),
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MaterialTheme.spacing.small),
                text = pokemon.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@ThemePreviews
@Composable
fun PokemonCardPreview() {
    PokedexTheme {
        PokemonCard(
            pokemon = Pokemon(
                id = 4,
                name = "Charmander",
                types = emptyList(),
                generation = 1,
                gameVersions = emptyList()
            ),
            context = LocalContext.current,
        )
    }
}
