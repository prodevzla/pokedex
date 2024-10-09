package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.PrimaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.InfoScreen
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.InfoScreenContentPreview
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonMore.MoreScreen
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonMoves.MovesScreen
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonStats.StatsScreen
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun PokemonViewPager(
    modifier: Modifier = Modifier,
    tabRowBackgroundColor: Color,
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
                PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]).offset(y = -8.dp),
                    color = MaterialTheme.colorScheme.surface,
                    width = 40.dp,
                    height = 8.dp,
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.background(tabRowBackgroundColor),

                    text = {
                        Text(
                            text = stringResource(title).uppercase(),
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 25.sp,
                            letterSpacing = 1.sp,
                            style = if (tabIndex == index) {
                                MaterialTheme.typography.titleMedium
                            } else {
                                MaterialTheme.typography.titleSmall
                            }
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }

        when (tabIndex) {
            0 -> {
                if (LocalInspectionMode.current) {
                    InfoScreenContentPreview()
                } else {
                    InfoScreen()
                }
            }

            1 -> StatsScreen()

            2 -> MovesScreen()

            3 -> MoreScreen()
        }
    }
}

@ThemePreviews
@Composable
fun PokemonViewPagerPreview() {
    PokedexTheme {
        Surface {
            PokemonViewPager(
                tabRowBackgroundColor = Color.Red,
            )
        }
    }
}
