@file:OptIn(ExperimentalMaterial3Api::class)

package com.prodevzla.pokedex.presentation.ability.model

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface AbilityUiState {
    data object Loading : AbilityUiState
    data class Content(
        val ability: Ability,
        val pokemons: List<Pokemon>,
        val sheetState: SheetValue
    ) : AbilityUiState

    data object Error : AbilityUiState
}
