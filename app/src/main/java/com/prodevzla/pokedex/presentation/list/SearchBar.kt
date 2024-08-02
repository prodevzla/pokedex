package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing
import com.prodevzla.pokedex.presentation.util.ThemePreviews

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    search: String,
    onSearchChange: (String) -> Unit = {},
) {
    val shape = RoundedCornerShape(
        topStartPercent = 50,
        topEndPercent = 5,
        bottomEndPercent = 50,
        bottomStartPercent = 5,
    )

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .background(
                color = Color.Blue,
                shape = shape,
            ),
        value = search,
        shape = shape,
        onValueChange = onSearchChange,
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = Color.White,
        ),
        colors = OutlinedTextFieldDefaults.colors().copy(
            cursorColor = Color.White,
        ),
    )
}

@ThemePreviews
@Composable
fun SearchBarPreview() {
    PokedexTheme {
        SearchBar(search = "Char")
    }
}
