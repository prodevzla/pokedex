package com.prodevzla.pokedex.presentation.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, tryAgain: () -> Unit = {}) {
    GenericContent(modifier) {
        Column {
            Text(
                text = "There was an error.\nPlease try again.",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
            Button(onClick = tryAgain) {
                Text(text = "Try again")
            }
        }

    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    GenericContent(modifier) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
fun GenericContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@ThemePreviews
@Composable
fun ErrorPreview() {
    PokedexTheme {
        Surface {
            ErrorScreen()
        }
    }
}

@ThemePreviews
@Composable
fun LoadingPreview() {
    PokedexTheme {
        Surface {
            LoadingScreen()
        }
    }
}
