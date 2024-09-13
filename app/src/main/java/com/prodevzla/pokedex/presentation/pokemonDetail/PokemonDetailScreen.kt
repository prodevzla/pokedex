package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.list.PokemonTypesRow
import com.prodevzla.pokedex.presentation.navigation.sharedKeyPokemonImage
import com.prodevzla.pokedex.presentation.navigation.sharedKeyPokemonName
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.presentation.util.lighten
import com.prodevzla.pokedex.presentation.util.sharedElementTransition
import com.prodevzla.pokedex.presentation.util.toTitle
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

//https://medium.com/@tunahan.bozkurt/custom-scroll-behavior-in-jetpack-compose-2d5a0e57d742

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onClickBack: () -> Unit,
) {
    CustomScaffold(
        modifier = modifier,
        topBarColor = pokemon.types.first().getColor(),
        title = {
            //the preview is showing a warning related to this composable I am passing to the customScaffold
            Text(
                text = pokemon.toTitle(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .sharedElementTransition(key = sharedKeyPokemonName + pokemon.id)
                    .takeIf { !LocalInspectionMode.current } ?: Modifier
            )

        },
        navIcon = {
            IconButton(onClickBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back")
            }
        }

    ) {
        Column(
            modifier = Modifier.background(color = pokemon.types.first().getColor())
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(pokemon.image.toString())
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(125.dp)
                    .align(Alignment.CenterHorizontally)
                    .sharedElementTransition(key = sharedKeyPokemonImage + pokemon.id)
            )
            PokemonTypesRow(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                types = pokemon.types
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            PokemonViewPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        pokemon.types
                            .first()
                            .getColor()
                            .lighten(0.8f)
                    ),
                tabRowBackgroundColor = pokemon.types.first().getColor(),
            )
        }
    }

}


@OptIn(ExperimentalSharedTransitionApi::class)
@ThemePreviews
@Composable
fun PokemonDetailScreenPreview() {
    PokedexTheme {
        Surface {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    PokemonDetailScreen(
                        pokemon = Pokemon(
                            id = 4,
                            name = "Charmander",
                            types = listOf(
                                PokemonType(
                                    id = 10,
                                    name = UiText.DynamicString("Fire")
                                )
                            ),
                            generation = 1,
                        ),
                        onClickBack = {}
                    )
                }
            }

        }
    }
}
