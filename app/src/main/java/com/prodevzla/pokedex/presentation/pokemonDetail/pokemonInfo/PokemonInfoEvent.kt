package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import com.prodevzla.pokedex.domain.model.Ability

sealed interface PokemonInfoEvent {
    data class TogglePlayVoiceover(val content: String): PokemonInfoEvent
    data class TogglePlayCry(val content: String): PokemonInfoEvent
    data object ScreenStopped: PokemonInfoEvent
    data class OnClickAbility(val ability: Ability): PokemonInfoEvent
}
