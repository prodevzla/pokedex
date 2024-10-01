package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun ColumnScope.CardTitle(modifier: Modifier = Modifier, @StringRes text: Int) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.align(Alignment.CenterHorizontally),
        fontWeight = FontWeight.Bold
    )
}

@ThemePreviews
@Composable
fun CardTitlePreview() {
    PokedexTheme {
        Surface {
            Column {
                CardTitle(text = R.string.tab_pokemon_info_abilities)
            }
        }
    }
}