@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.prodevzla.pokedex.presentation.list.composable

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.navigation.sharedKeyPokemonImage
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.darken
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.presentation.util.sharedElementTransition
import com.prodevzla.pokedex.presentation.util.toTitle
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

//context(SharedTransitionScope, AnimatedVisibilityScope)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onClickItem: (Pokemon) -> Unit = {},
    onToggleSave: (Pokemon) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: AnimatedVisibilityScope?,
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .padding(MaterialTheme.spacing.small), // distance between cards
        shape = RoundedCornerShape(MaterialTheme.spacing.small),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.surface

        ),
        onClick = {
            onClickItem(pokemon)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = pokemon.types
                        .first()
                        .getColor()
                )
                .padding(start = MaterialTheme.spacing.medium)
        ) {

            Column(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Row {
                    Text(
                        text = pokemon.toTitle(),
                        style = MaterialTheme.typography.titleMedium,
                        color = pokemon.types.first().getColor().darken(0.5f),
                        modifier = Modifier.weight(1f)
                    )

                    SaveButton(
                        isSaved = pokemon.isSaved,
                        onClick = {
                            onToggleSave(pokemon)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                PokemonTypesRow(types = pokemon.types)

            }

            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .background(
                        color = Color.White,
                        shape = imageBackgroundShape
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(pokemon.image.toString())
                        .diskCacheKey("cachePokemonCard${pokemon.name}")
                        //.placeholder(R.drawable.charmeleon)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = pokemon.types
                                .first()
                                .getColor()
                                .copy(alpha = 0.6f),
                            shape = imageBackgroundShape
                        )
                        .sharedElementTransition(
                            key = sharedKeyPokemonImage + pokemon.id,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }
        }
    }
}

@Composable
fun PokemonTypesRow(modifier: Modifier = Modifier, types: List<PokemonType>) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
        types.forEach { type ->
            Text(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 0.5.dp, // border width
                        color = types
                            .first()
                            .getColor()
                            .darken(0.5f),
                        shape = RoundedCornerShape(4.dp)
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

val imageBackgroundShape = RoundedCornerShape(
    topStartPercent = 50,
    topEndPercent = 5,
    bottomEndPercent = 50,
    bottomStartPercent = 50,
)

@ThemePreviews
@Composable
fun PokemonCardPreview() {
    PokedexTheme {
        Surface {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    PokemonCard(
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
                            isSaved = true,
                            abilities = listOf(1,2)
                            //gameVersions = emptyList()
                        ),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
        }

    }
}

@ThemePreviews
@Composable
fun PokemonCardUnsavedPreview() {
    PokedexTheme {
        Surface {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    PokemonCard(
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
                            isSaved = false,
                            abilities = listOf(1,2)
                            //gameVersions = emptyList()
                        ),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this
                    )
                }
            }
        }

    }
}
