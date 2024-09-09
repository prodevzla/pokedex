package com.prodevzla.pokedex.presentation.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow

@Composable
fun ListAppBarTitle() {
    Text(
        text = "Pokedex",
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge.copy(
            shadow = Shadow(
                color = Color.Blue,
                offset = Offset(5.0f, 10.0f),
                blurRadius = 3f
            )
        )
    )
}