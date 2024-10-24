package com.prodevzla.pokedex.presentation.abilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.presentation.abilities.model.AbilitiesUiState
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.ErrorScreen
import com.prodevzla.pokedex.presentation.util.LoadingScreen
import com.prodevzla.pokedex.presentation.util.PreviewData
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun AbilitiesScreen(
    modifier: Modifier = Modifier,
    viewModel: AbilitiesViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onEvent: (AbilitiesScreenEvent) -> Unit = remember(viewModel) {
        return@remember viewModel::onEvent
    }

    AbilitiesScreenContent(modifier, state, onEvent = { event ->
        when (event) {
            AbilitiesScreenEvent.OnClickBack -> onClickBack.invoke()
            else -> onEvent.invoke(event)
        }
    })

}

@Composable
fun AbilitiesScreenContent(
    modifier: Modifier = Modifier,
    state: AbilitiesUiState,
    onEvent: (AbilitiesScreenEvent) -> Unit
) {
    CustomScaffold(
        title = {
            Text(
                text = "Abilities",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navIcon = {
            IconButton(onClick = {
                onEvent(AbilitiesScreenEvent.OnClickBack)
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back")
            }
        },
    ) {
        when (state) {
            AbilitiesUiState.Loading -> LoadingScreen()
            AbilitiesUiState.Error -> ErrorScreen()
            is AbilitiesUiState.Content -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    items(state.abilities, key = { it.id }) { ability ->
                        AbilityCard(
                            ability = ability,
                            onClick = {
                                onEvent.invoke(AbilitiesScreenEvent.OnClickAbility(ability))
                            }
                        )
                    }
                }
            }
        }

    }

}

@Composable
fun AbilityCard(modifier: Modifier = Modifier, ability: Ability, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.medium),
        onClick = onClick
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium))
        {
            Text(ability.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(MaterialTheme.spacing.medium))
            Text(ability.shortEffect, style = MaterialTheme.typography.titleSmall)

        }
    }
}


@ThemePreviews
@Composable
fun AbilitiesScreenPreview() {
    PokedexTheme {
        Surface {
            AbilitiesScreenContent(state = AbilitiesUiState.Content(
                abilities = PreviewData.abilities
            ), onEvent = {}

            )
        }
    }
}

@ThemePreviews
@Composable
fun AbilityCardPreview() {
    PokedexTheme {
        Surface {
            AbilityCard(
                ability = PreviewData.ability1,
                onClick = {}
            )
        }
    }
}
