package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

sealed interface PokemonInfoEvent {
    data class TogglePlayVoiceover(val content: String): PokemonInfoEvent
    data class TogglePlayCry(val content: String): PokemonInfoEvent
    data object ScreenStopped: PokemonInfoEvent
}
