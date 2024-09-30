package com.prodevzla.pokedex.presentation.list.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    isSaved: Boolean,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier.size(16.dp),
        onClick = onClick
    ) {
        if (isSaved) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = Color.Red,
                contentDescription = "saved",
            )
        }
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            tint = Color.Black,
            contentDescription = "saved",
        )

    }
}

@ThemePreviews
@Composable
fun SavedPreview() {
    PokedexTheme {
        Surface {
            SaveButton(isSaved = true)
        }
    }
}

@ThemePreviews
@Composable
fun UnsavedPreview() {
    PokedexTheme {
        Surface {
            SaveButton(isSaved = false)
        }
    }
}
