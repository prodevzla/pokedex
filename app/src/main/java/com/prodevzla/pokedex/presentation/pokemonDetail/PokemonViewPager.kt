package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonAdditionalInfo
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.PokemonMoves
import com.prodevzla.pokedex.domain.model.PokemonStats
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.LoadingScreen
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun PokemonViewPager(
    modifier: Modifier = Modifier,
    indicatorColor: Color,
    state: DetailUiState
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        R.string.tab_info,
        R.string.tab_stats,
        R.string.tab_moves,
        R.string.tab_more
    )

    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(4.dp)
                        .background(indicatorColor)
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = stringResource(title).uppercase(),
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 25.sp,
                            letterSpacing = 1.sp
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }

        when (tabIndex) {
            0 -> GenericViewPagerContent<PokemonInfo>(state.info) {
                InfoContent(state = it)
            }

            1 -> GenericViewPagerContent<PokemonStats>(state.stats)  {
                StatsContent(state = it)
            }

            2 -> GenericViewPagerContent<PokemonMoves>(state.moves)  {
                MovesContent(state = it)
            }

            3 -> GenericViewPagerContent<PokemonAdditionalInfo>(state.additionalInfo)  {
                MoreContent(state = it)
            }
        }
    }
}

@Composable
fun <T>GenericViewPagerContent(state: CategoryUiState, content: @Composable (T) -> Unit) {
    when (state) {
        is CategoryUiState.Content<*> -> {
            content(state.content as T)
        }

        CategoryUiState.Error -> {
            ErrorScreen()
        }

        CategoryUiState.Loading -> {
            LoadingScreen()
        }
    }
}

@ThemePreviews
@Composable
fun PokemonViewPagerPreview() {
    PokedexTheme {
        Surface {
            PokemonViewPager(
                indicatorColor = Color.Red,
                state = DetailUiState(
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
                )
            )
        }
    }
}
