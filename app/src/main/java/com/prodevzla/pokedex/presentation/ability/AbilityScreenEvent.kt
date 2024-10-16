@file:OptIn(ExperimentalMaterial3Api::class)

package com.prodevzla.pokedex.presentation.ability

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface AbilityScreenEvent {
    data class OnClickPokemon(val pokemon: Pokemon) : AbilityScreenEvent
    data class ToggleSave(val pokemon: Pokemon) : AbilityScreenEvent
    data class SheetStateChange(val sheetState: SheetValue) : AbilityScreenEvent
    data object ClickTryAgain : AbilityScreenEvent
}
