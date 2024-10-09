package com.prodevzla.pokedex.presentation.ability

import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface AbilityScreenEvent {
    data class OnClickPokemon(val pokemon: Pokemon): AbilityScreenEvent
    data class ToggleSave(val pokemon: Pokemon): AbilityScreenEvent
}
