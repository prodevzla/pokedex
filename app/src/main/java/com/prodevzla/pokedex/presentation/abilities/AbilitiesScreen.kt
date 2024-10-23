package com.prodevzla.pokedex.presentation.abilities

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.presentation.util.CustomScaffold
import com.prodevzla.pokedex.presentation.util.PreviewData
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.ui.theme.PokedexTheme

@Composable
fun AbilitiesScreen(
    modifier: Modifier = Modifier,
    //viewModel: AbilitiesViewModel = hiltViewModel()
) {

    CustomScaffold(
        modifier = modifier,
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
                //onEvent(PokemonDetailEvent.OnClickBack)
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "navigate back")
            }
        },
    ) {

    }
}

@Composable
fun AbilityCard(modifier: Modifier = Modifier, ability: Ability) {
    Card(modifier = modifier, onClick = {}) {
        ListItem(
            headlineContent = {
                Text(ability.name, style = MaterialTheme.typography.titleMedium)
            },
            supportingContent = {
                Text(ability.shortEffect, style = MaterialTheme.typography.titleSmall)
            }
        )
    }
}


@ThemePreviews
@Composable
fun AbilitiesScreenPreview() {
    PokedexTheme {
        Surface {
            AbilitiesScreen()
        }
    }
}

@ThemePreviews
@Composable
fun AbilityCardPreview() {
    PokedexTheme {
        Surface {
            AbilityCard(
                ability = PreviewData.ability
            )
        }
    }
}