package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun InfoContent(modifier: Modifier = Modifier, state: PokemonInfo) {
    Column (modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
//        repeat(200) {
//            Text("info content: $it")
//        }
    }
}

@ThemePreviews
@Composable
fun InfoContentPreview() {
    PokedexTheme {
        Surface {
            InfoContent(
                state = PokemonInfo(
                    height = 4733,
                    weight = 8327,
                    genderRate = 8498,
                    flavorText = "pellentesque",
                    cries = "vero"

                )
            )
        }
    }
}
