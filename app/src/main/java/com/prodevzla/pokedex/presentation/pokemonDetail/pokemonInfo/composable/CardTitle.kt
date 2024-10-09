package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun CardTitle(modifier: Modifier = Modifier, @StringRes text: Int) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@ThemePreviews
@Composable
fun CardTitlePreview() {
    PokedexTheme {
        Surface {
            CardTitle(text = R.string.tab_pokemon_info_abilities)
        }
    }
}