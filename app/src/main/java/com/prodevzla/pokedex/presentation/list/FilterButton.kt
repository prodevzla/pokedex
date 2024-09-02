package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterOption
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.asString
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    filter: Filter,
    onClick: (Filter) -> Unit = {},
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = filter.getColor()
        ),
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        onClick = { onClick(filter) }
    ) {
        Text(
            text = filter.selectedItem.name.asString().uppercase(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@ThemePreviews
@Composable
fun FilterButtonPreview() {
    PokedexTheme {
        Surface {
            FilterButton(
                filter = Filter(
                    dialogTitle = UiText.DynamicString("Select type"),
                    weight = 1.0f,
                    selection = 0,
                    values = listOf(
                        PokemonType(
                            id = 10,
                            name = UiText.DynamicString("Fire")
                        )
                    ),
                    onClickSelection = {},
                    filterOption = FilterOption.TYPE,
                )
            )
        }
    }
}
