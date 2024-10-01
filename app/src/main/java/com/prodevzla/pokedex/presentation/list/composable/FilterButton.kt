package com.prodevzla.pokedex.presentation.list.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.asString
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    text: UiText,
    color: Color,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = color//filter.getColor()
        ),
        shape = RoundedCornerShape(MaterialTheme.spacing.small),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Text(
            text = text.asString().uppercase(),//filter.selectedItem.name.asString().uppercase(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@ThemePreviews
@Composable
fun FilterButtonPreview() {
    PokedexTheme {
        Surface {
            FilterButton(
                text = UiText.DynamicString("Select type"),
                color = PokemonType(id = 1, name = UiText.DynamicString("normal")).getColor(),
            )
        }
    }
}
