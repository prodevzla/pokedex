@file:OptIn(ExperimentalMaterial3Api::class)

package com.prodevzla.pokedex.presentation.ability

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun AbilityScreen(
    modifier: Modifier = Modifier,
    ability: Ability,
    viewModel: AbilityViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = onDismiss,
    ) {
        AbilityScreenContent(
            modifier = modifier,
            ability = ability
        )
    }
}

@Composable
fun AbilityScreenContent(modifier: Modifier = Modifier, ability: Ability) {
    Column(
        modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)
    ) {
        Text("AbilityContent")
        Text(ability.name)
    }


}

@ThemePreviews
@Composable
fun AbilityScreenPreview() {
    PokedexTheme {
        Surface {
            AbilityScreenContent(
                ability = Ability(
                    name = "Overgrow",
                    description = "Powers up Grass-type moves when the Pokemon's HP is low",
                    isHidden = false

                )
            )
        }
    }
}