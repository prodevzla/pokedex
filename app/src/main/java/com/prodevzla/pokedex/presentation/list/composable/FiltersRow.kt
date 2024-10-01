package com.prodevzla.pokedex.presentation.list.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.getColor
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
                text = it.selectedItem.name,
                color = it.getColor(),
                onClick = {
                    onClickFilter.invoke(it)
                },
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
                    Filter(
                        dialogTitle = UiText.DynamicString("select generation"),
                        weight = 1.0f,
                        selection = 0,
                        values = listOf(
                            PokemonGeneration(
                                id = 1,
                                name = UiText.DynamicString("Gen I")
                            )
                        ),
                        type = FilterType.GENERATION,
                    ),
                    Filter(
                        dialogTitle = UiText.DynamicString("select type"),
                        weight = 1.0f,
                        selection = 0,
                        values = listOf(
                            PokemonGeneration(
                                id = 1,
                                name = UiText.DynamicString("Gen I")
                            )
                        ),
                        type = FilterType.TYPE,
                    )
                )
            )
        }
    }
}
