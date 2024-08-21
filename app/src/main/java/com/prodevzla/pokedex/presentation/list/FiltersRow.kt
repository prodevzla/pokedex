package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun FiltersRow(
    modifier: Modifier = Modifier,
    onClickFilterType: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        FilterButton(modifier = Modifier.weight(2f), text = "all game versions")
        FilterButton(modifier = Modifier.weight(1f), text = "all gens")
        FilterButton(
            modifier = Modifier.weight(1f),
            text = "all types",
            onClick = onClickFilterType
        )
    }
}

@ThemePreviews
@Composable
fun FiltersRowPreview() {
    PokedexTheme {
        Surface {
            FiltersRow()
        }
    }
}