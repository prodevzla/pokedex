package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.prodevzla.pokedex.model.domain.PokemonType
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.NeutralGrey
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTypeBottomSheet(
    modifier: Modifier = Modifier,
    pokemonTypes: List<PokemonType>,
    onDismiss: () -> Unit = {},
    onClickType: (Int) -> Unit = {}
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        FilterTypeSheetContent(
            pokemonTypes = pokemonTypes,
            onClickType = onClickType
        )
    }
}

@Composable
fun FilterTypeSheetContent(
    modifier: Modifier = Modifier,
    pokemonTypes: List<PokemonType>,
    onClickType: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(horizontal = MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Select type",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(pokemonTypes) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClickType.invoke(it.id) },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = it.getColor()
                ),
            ) {
                Text(
                    text = it.name.uppercase(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

fun PokemonType.getColor(): Color {
    return when(this.id) {
        1 -> Color(168,168,125)
        2 -> Color(133,55,45)
        3 -> Color(164,145,234)
        4 -> Color(149,70,155)
        5 -> Color(219,193,117)
        6 -> Color(180,161,75)
        7 -> Color(171,184,66)
        8 -> Color(108,89,148)
        9 -> Color(222,92,62)
        10 -> Color(222,92,62)

        11 -> Color(112,143,233)
        12 -> Color(139,198,96)
        13 -> Color(242,210,84)
        14 -> Color(230,99,136)
        15 -> Color(166,214,215)
        16 -> Color(105,59,239)
        17 -> Color(108,89,74)
        18 -> Color(235,164,221)
        else -> NeutralGrey
    }
}

@ThemePreviews
@Composable
fun FilterTypeBottomSheetPreview() {
    PokedexTheme {
        Surface {
            FilterTypeSheetContent(
                pokemonTypes = listOf(
                    PokemonType(id = 1, name = "normal"),
                    PokemonType(id = 2, name = "fighting"),
                    PokemonType(id = 3, name = "flying"),
                    PokemonType(id = 4, name = "poison")
                )
            )
        }
    }
}
