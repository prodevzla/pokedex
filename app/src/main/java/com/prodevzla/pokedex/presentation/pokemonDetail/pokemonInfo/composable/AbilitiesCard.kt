package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun AbilitiesCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    abilities: List<String>?,
    pokemonType: PokemonType?,
) {
    ExpandableCard(modifier = modifier, isLoading = isLoading) {
        if (abilities == null) {
            return@ExpandableCard
        }
        Column {
            abilities.forEach {
                InfoDetailBox(
                    boxBackgroundColor = pokemonType?.getColor(),
                    label = "", content = {
                        InfoDetailText(text = it)
                    }

                )
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
                abilities = listOf("ability 1"),
                pokemonType = PokemonType(
                    id = 1,
                    name = UiText.DynamicString("Normal")
                )

            )
        }
    }
}