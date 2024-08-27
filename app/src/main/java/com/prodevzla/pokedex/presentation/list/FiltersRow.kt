package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun FiltersRow(
    modifier: Modifier = Modifier,
    filters: List<Filter>,
    onClickFilter: (Filter) -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        filters.forEach {
            FilterButton(
                modifier = Modifier.weight(it.weight),
                filter = it,
                onClick = onClickFilter
            )
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
//                    Filter.Version(
//                        weight = 1.5f,
//                    ),
                    Filter.Generation(
                        weight = 1.0f,
                        selection = PokemonGeneration(
                            id = 1,
                            name = UiText.DynamicString("Gen I")
                        ),
                        values = emptyList(),
                        onClickSelection = {},
                    ),
                    Filter.Type(
                        weight = 1.0f,
                        selection = PokemonType(
                            id = 10,
                            name = UiText.DynamicString("Fire")
                        ),
                        values = emptyList(),
                        onClickSelection = {},
                    )
                )
            )
        }
    }
}
