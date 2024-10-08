package com.prodevzla.pokedex.presentation.ability.model

import com.prodevzla.pokedex.domain.model.Ability

sealed interface AbilityUiState {
    data object Loading: AbilityUiState
    data class Content(val ability: Ability): AbilityUiState
    data object Error: AbilityUiState
}
