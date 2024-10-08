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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.presentation.ability.model.AbilityUiState
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.LoadingScreen
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun AbilityScreen(
    modifier: Modifier = Modifier,
    abilityId: Int,
    viewModel: AbilityViewModel = hiltViewModel(
        key = "ability$abilityId",
        creationCallback = { factory: AbilityViewModel.MyViewModelFactory ->
            factory.create(abilityId)
        }
    ),
    onDismiss: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ModalBottomSheet(
        modifier = modifier,
        containerColor = Color.Blue,
        onDismissRequest = onDismiss,
    ) {

        when (state) {
            AbilityUiState.Loading -> LoadingScreen()
            AbilityUiState.Error -> ErrorScreen(tryAgain = {

            })

            is AbilityUiState.Content -> {

                AbilityScreenContent(
                    modifier = modifier,
                    ability = (state as AbilityUiState.Content).ability
                )
            }
        }

    }
}

@Composable
fun AbilityScreenContent(modifier: Modifier = Modifier, ability: Ability) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(ability.id.toString())
        Text(ability.flavorText)
    }


}

@ThemePreviews
@Composable
fun AbilityScreenPreview() {
    PokedexTheme {
        Surface {
            AbilityScreenContent(
                ability = Ability(
                    id = 6260,
                    name = "Overgrow",
                    flavorText = "Overgrow",
                    shortEffect = "Powers up Grass-type moves when the Pokemon's HP is low",
                    longEffect = "Powers up Grass-type moves when the Pokemon's HP is low asdasd asd"
                )
            )
        }
    }
}