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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
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
        ExpandableCard(isLoading = true) {}
    }
}

@ThemePreviews
@PreviewScreenSizes
@Composable
fun ExpandableCardNotLoadingPreview() {
    PokedexTheme {
        ExpandableCard(isLoading = false) {
            Text(LoremIpsum(words = 25).values.toList().first().toString())
        }
    }
}
