package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.presentation.util.ExpandableCard
import com.prodevzla.pokedex.presentation.util.ThemePreviews
import com.prodevzla.pokedex.presentation.util.getColor
import com.prodevzla.pokedex.ui.theme.PokedexTheme
import com.prodevzla.pokedex.ui.theme.spacing

@Composable
fun AbilitiesCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    abilities: List<Ability>?,
    pokemonType: PokemonType?,
) {
    ExpandableCard(modifier = modifier, isLoading = isLoading) {
        if (abilities == null || pokemonType == null) {
            return@ExpandableCard
        }
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
            abilities.forEach {
                AbilityBox(
                    boxBackgroundColor = pokemonType.getColor(),
                    ability = it,
                )
            }
        }

    }
}

@Composable
fun AbilityBox(
    modifier: Modifier = Modifier,
    boxBackgroundColor: Color,
    ability: Ability,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = boxBackgroundColor
                    ),
                    shape = RoundedCornerShape(MaterialTheme.spacing.small)
                )
                .background(
                    color = boxBackgroundColor,
                    shape = RoundedCornerShape(MaterialTheme.spacing.small)
                )

        ) {
            Text(
                modifier = modifier
                    .padding(MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                text = ability.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Text(
            text = ability.description + ability.isHidden.toString(), //TODO continue here, show a hidden label when the ability is hidden
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@ThemePreviews
@Composable
fun AbilitiesCardPreview() {
    PokedexTheme {
        Surface {
            AbilitiesCard(
                isLoading = false,
                abilities = listOf(
                    Ability(
                        name = "Run away",
                        description = "Enables a sure getaway from wild Pokemon",
                        isHidden = false
                    ),
                    Ability(
                        name = "Hustle",
                        description = "Boosts the attack stat, but lowers accuracy",
                        isHidden = true
                    )
                ),
                pokemonType = PokemonType(
                    id = 1,
                    name = UiText.DynamicString("Normal")
                )

            )
        }
    }
}