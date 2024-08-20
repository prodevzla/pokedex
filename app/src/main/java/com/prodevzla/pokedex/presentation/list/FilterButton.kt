package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Text(text = text.uppercase())
    }
}

@ThemePreviews
@Composable
fun FilterButtonPreview() {
    PokedexTheme {
        Surface {
            FilterButton(text = "Game version")
        }
    }
}
