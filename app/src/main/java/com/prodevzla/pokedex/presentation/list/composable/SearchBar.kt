package com.prodevzla.pokedex.presentation.list.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    search: String,
    onSearchChange: (String) -> Unit = {},
    onClickClose: () -> Unit = {},
) {
    val shape = RoundedCornerShape(
        topStartPercent = 25,
        topEndPercent = 25,
        bottomEndPercent = 5,
        bottomStartPercent = 5,
    )

    TextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = shape
            ),
        shape = shape,
        maxLines = 1,
        textStyle = MaterialTheme.typography.titleMedium,
        value = TextFieldValue(
            text = search,
            selection = TextRange(search.length) // TextRange(0, textValue.length) -> Select that text in a color
        ),
        onValueChange = {
            onSearchChange.invoke(it.text)
        },
        colors = OutlinedTextFieldDefaults.colors().copy(
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        leadingIcon = {
            Image(painter = painterResource(id = R.drawable.pokeball), contentDescription = "close")
        },
        trailingIcon = {
            IconButton(onClick = onClickClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "close")
            }
        },
    )
}

@ThemePreviews
@Composable
fun SearchBarPreview() {
    PokedexTheme {
        SearchBar(search = "Char")
    }
}
