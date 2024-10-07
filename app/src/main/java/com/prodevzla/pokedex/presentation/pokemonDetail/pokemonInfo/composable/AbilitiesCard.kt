package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prodevzla.pokedex.R
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
    onClickAbility: (Ability) -> Unit = {},
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
                    onClickAbility = onClickAbility,
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
    onClickAbility: (Ability) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
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
                    color = if (ability.isHidden) MaterialTheme.colorScheme.surface else boxBackgroundColor,
                    shape = RoundedCornerShape(MaterialTheme.spacing.small)
                )
                .clickable {
                    onClickAbility.invoke(ability)
                }
        ) {
            if (ability.isHidden) {
                Text(
                    text = stringResource(R.string.ability_hidden),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(
                            color = boxBackgroundColor,
                            shape = RoundedCornerShape(
                                topStart = MaterialTheme.spacing.small,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp,
                                bottomStart = MaterialTheme.spacing.small
                            )
                        )
                        .padding(
                            vertical = MaterialTheme.spacing.small,
                            horizontal = MaterialTheme.spacing.medium
                        ),
                )
            }

            Text(
                modifier = modifier
                    .padding(MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                text = ability.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Icon(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
                    .wrapContentHeight()
                    .align(Alignment.CenterEnd),
                imageVector = Icons.Outlined.Info,
                contentDescription = "ability info"
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

        Text(
            text = ability.description,
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
