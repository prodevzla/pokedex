package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo

import android.net.Uri

sealed interface PokemonInfoEvent {
    data class PlayVoiceover(val content: String): PokemonInfoEvent
    data object StopVoiceover: PokemonInfoEvent
    data class PlayCry(val content: Uri): PokemonInfoEvent
    data object StopCry: PokemonInfoEvent
    data object ScreenStopped: PokemonInfoEvent
}