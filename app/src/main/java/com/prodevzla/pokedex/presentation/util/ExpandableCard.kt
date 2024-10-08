package com.prodevzla.pokedex.presentation.util

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.padding(MaterialTheme.spacing.medium),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.medium)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
                .animateContentSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                return@Column
            }
            content()
        }
    }
}

@ThemePreviews
@Composable
fun ExpandableCardLoadingPreview() {
    PokedexTheme {
        Surface {
            ExpandableCard(isLoading = true) {}
        }
    }
}

@ThemePreviews
@Composable
fun ExpandableCardNotLoadingPreview() {
    PokedexTheme {
        Surface {
            ExpandableCard(isLoading = false) {
                Text("Test")
            }
        }
    }
}
