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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.pokemonFontFamily

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
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            color = Color.Black,
                            style = TextStyle(
                                fontFamily = pokemonFontFamily,
                                fontSize = 24.sp,
                                shadow = Shadow(
                                    color = Color.Blue,
                                    offset = Offset(5.0f, 10.0f),
                                    blurRadius = 3f
                                )
                            )
                        )

                    },
                    colors = TopAppBarColors(
                        containerColor = Color.Black,
                        scrolledContainerColor = Color.Black,
                        navigationIconContentColor = Color.Blue,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White

                    )
                )
                HorizontalDivider()
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF87CEFA),  // Lighter blue
                            Color(0xFF0000FF), // Darker blue
                        )
                    )
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun CustomScaffoldPreview() {
    PokedexTheme {
        CustomScaffold(modifier = Modifier, title = "Custom Scaffold") {
            Text(text = "Hello")
        }
    }
}
