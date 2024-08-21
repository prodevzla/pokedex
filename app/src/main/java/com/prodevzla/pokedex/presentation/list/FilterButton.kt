package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.domain.Filter
import com.prodevzla.pokedex.domain.FilterType
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.NeutralGrey
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    filter: Filter,
    onClick: (FilterType) -> Unit = {},
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = filter.color
        ),
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        onClick = { onClick(filter.type) }
    ) {
        Text(
            text = filter.label.uppercase(),
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
                    label = "all types",
                    type = FilterType.TYPES,
                    weight = 1f,
                    color = NeutralGrey,
                )
            )
        }
    }
}
