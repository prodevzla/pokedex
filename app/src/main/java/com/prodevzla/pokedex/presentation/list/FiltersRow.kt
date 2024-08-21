package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.domain.Filter
import com.prodevzla.pokedex.domain.FilterType
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun FiltersRow(
    modifier: Modifier = Modifier,
    filters: List<Filter>,
    onClickFilter: (FilterType) -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        filters.forEach {
            FilterButton(
                modifier = Modifier.weight(it.weight),
                text = it.label,
                onClick = { onClickFilter(it.type) })
        }

    }
}

@ThemePreviews
@Composable
fun FiltersRowPreview() {
    PokedexTheme {
        Surface {
            FiltersRow(
                filters = listOf(
                    Filter(
                        label = "all game versions",
                        type = FilterType.VERSIONS,
                        weight = 1.5f
                    ),
                    Filter(
                        label = "all gens",
                        type = FilterType.GENERATIONS,
                        weight = 1.0f
                    ),
                    Filter(
                        label = "all types",
                        type = FilterType.TYPES,
                        weight = 1.0f
                    )
                )
            )
        }
    }
}