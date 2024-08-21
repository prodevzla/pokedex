package com.prodevzla.pokedex.presentation.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.prodevzla.pokedex.model.domain.PokemonType
import com.prodevzla.pokedex.presentation.util.ThemePreviews
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
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(pokemonTypes) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClickType.invoke(it.id) },
                shape = RectangleShape
            ) {
                Text(text = it.name.uppercase())
            }
        }
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