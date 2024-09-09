package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun StatsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text("stats")
    }
}

@ThemePreviews
@Composable
fun StatsContentPreview() {
    PokedexTheme {
        Surface {
            StatsContent()
        }
    }
}