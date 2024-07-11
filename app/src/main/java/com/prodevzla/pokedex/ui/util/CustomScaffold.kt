package com.prodevzla.pokedex.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
        Column {
            CenterAlignedTopAppBar(title = {
                Text(text = title)
            })
            HorizontalDivider()
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF87CEFA),  // Lighter blue
                        Color(0xFF0000FF), // Darker blue
                    )
                ))
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun CustomScaffoldPreview() {
    CustomScaffold(modifier = Modifier, title = "Custom Scaffold") {
        Text(text = "Hello")
    }
}