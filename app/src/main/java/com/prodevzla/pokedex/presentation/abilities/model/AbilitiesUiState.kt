package com.prodevzla.pokedex.presentation.abilities.model

import com.prodevzla.pokedex.domain.model.Ability

sealed interface AbilitiesUiState {

    data object Loading : AbilitiesUiState

    data class Content(
        val abilities: List<Ability>,
        val showAbilityDialog: Ability?,
    ) : AbilitiesUiState

    data object Error : AbilitiesUiState

}
