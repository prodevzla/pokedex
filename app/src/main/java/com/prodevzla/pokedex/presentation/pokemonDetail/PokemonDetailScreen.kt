package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.navigation.sharedKeyPokemonImage
import com.prodevzla.pokedex.presentation.navigation.sharedKeyPokemonName
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.presentation.util.sharedElementTransition
import com.prodevzla.pokedex.presentation.util.toTitle
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import kotlin.math.roundToInt

context(SharedTransitionScope, AnimatedVisibilityScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
                modifier = Modifier.sharedElementTransition(key = sharedKeyPokemonName + pokemon.id)
            )

        },
        navIcon = {
            IconButton(onClickBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back")
            }
        }

    ) {
        val marginTopInPx = LocalDensity.current.run { 0.dp.toPx() }

        val imageHeightInPx = LocalDensity.current.run { 150.dp.toPx() }

        var offset by remember { mutableFloatStateOf(0f) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    offset = (offset + delta).coerceIn(marginTopInPx, imageHeightInPx)
                    return Offset(
                        0f,
                        if (offset < imageHeightInPx && offset > marginTopInPx) delta else 0f
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .background(color = pokemon.types.first().getColor())
                .nestedScroll(nestedScrollConnection)
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
                    .align(Alignment.TopCenter)
                    .onGloballyPositioned {
                        offset = imageHeightInPx
                    }
                    .sharedElementTransition(key = sharedKeyPokemonImage + pokemon.id)
            )
            PokemonViewPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(0, offset.roundToInt())
                    },
                indicatorColor = pokemon.types.first().getColor(),
                state = state,
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
