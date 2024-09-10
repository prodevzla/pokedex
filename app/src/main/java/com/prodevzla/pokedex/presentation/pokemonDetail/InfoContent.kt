package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun InfoContent(modifier: Modifier = Modifier, state: CategoryUiState) {
    when (state) {
        is CategoryUiState.Content<*> -> {
            Text("content")
        }

        CategoryUiState.Error -> {
            Text("error")
        }

        CategoryUiState.Loading -> {
            Text("loading")
        }
    }

}

@ThemePreviews
@Composable
fun InfoContentPreview() {
    PokedexTheme {
        Surface {
            InfoContent(
                state = CategoryUiState.Content(
                    content = PokemonInfo(
                        height = 4733,
                        wight = 8327,
                        genderRate = 8498,
                        flavorText = "pellentesque",
                        cries = "vero"

                    )
                )
            )
        }
    }
}
