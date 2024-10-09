package com.prodevzla.pokedex.presentation.ability.model

import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface AbilityUiState {
    data object Loading: AbilityUiState
    data class Content(val ability: Ability, val pokemons: List<Pokemon>): AbilityUiState
    data object Error: AbilityUiState
}
