package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun AbilitiesCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    abilities: List<String>?
) {
    ExpandableCard(modifier = modifier, isLoading = isLoading) {
        if (abilities == null) {
            return@ExpandableCard
        }
        Column {
            abilities.forEach {
                Text(it)
            }

        }

    }
}

@ThemePreviews
@Composable
fun AbilitiesCardPreview() {
    PokedexTheme {
        Surface {
            AbilitiesCard(
                isLoading = false,
                abilities = listOf("ability 1")

            )
        }
    }
}