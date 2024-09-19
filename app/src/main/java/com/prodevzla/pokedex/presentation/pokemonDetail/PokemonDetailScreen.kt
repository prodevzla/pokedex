package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.list.imageBackgroundShape
import com.prodevzla.pokedex.presentation.navigation.sharedKeyPokemonImage
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.darken
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.presentation.util.getUIId
import com.prodevzla.pokedex.presentation.util.getUIName
import com.prodevzla.pokedex.presentation.util.lighten
import com.prodevzla.pokedex.presentation.util.sharedElementTransition
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

//https://medium.com/@tunahan.bozkurt/custom-scroll-behavior-in-jetpack-compose-2d5a0e57d742

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    viewModel: PokemonDetailsViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
) {

    val pokemonVm = viewModel.uiState.collectAsStateWithLifecycle()


    CustomScaffold(
        modifier = modifier,
        topBarColor = pokemon.types.first().getColor(),
        title = {
            Text(
                text = pokemon.getUIName(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navIcon = {
            IconButton(onClickBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back")
            }
        },
        actions = {
            val isSaved: Boolean = pokemonVm.value?.isSaved ?: false

            val (icon: ImageVector, tint: Color) = if (isSaved) {
                Icons.Filled.Favorite to Color.Red
            } else {
                Icons.Default.FavoriteBorder to MaterialTheme.colorScheme.onSurface
            }

            IconButton(
                onClick = {
                    viewModel.onEvent(PokemonDetailEvent.SaveClick)
                }
            ) {
                Icon(
                    imageVector = icon,
                    tint = tint,
                    contentDescription = "saved"
                )
            }
        },

        ) {
        Column(
            modifier = Modifier.background(color = pokemon.types.first().getColor())
        ) {

            PokemonDetailHeader(pokemon = pokemon)

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

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonDetailHeader(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = pokemon.getUIId(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            PokemonTypesColumn(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium),
                types = pokemon.types
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(95.dp)
                .weight(0.5f)
                .background(
                    color = Color.White,
                    shape = imageBackgroundShape
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(pokemon.image.toString())
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()

                    .background(
                        color = pokemon.types
                            .first()
                            .getColor()
                            .copy(alpha = 0.6f),
                        shape = imageBackgroundShape
                    )
                    .sharedElementTransition(key = sharedKeyPokemonImage + pokemon.id)
            )
        }

    }
}

@Composable
fun PokemonTypesColumn(modifier: Modifier = Modifier, types: List<PokemonType>) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        types.forEach { type ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.5.dp, // border width
                        color = types
                            .first()
                            .getColor()
                            .darken(0.5f),
                        shape = RoundedCornerShape(4.dp) // adjust the corner radius as needed
                    )
                    .padding(4.dp),

                text = type.name.value.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = types.first().getColor().darken(0.5f),
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
                            image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
                            isSaved = false
                        ),
                        onClickBack = {}
                    )
                }
            }

        }
    }
}
