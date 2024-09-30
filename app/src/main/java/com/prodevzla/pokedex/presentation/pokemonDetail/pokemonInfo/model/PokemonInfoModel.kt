package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model

import com.prodevzla.pokedex.domain.model.AudioPlaybackState
import com.prodevzla.pokedex.domain.model.UiText

data class PokemonSpec(
    val height: UiText,
    val weight: UiText,
//    val genderRate: Int,
    val flavorText: String,
    val cry: String,
    val statePlayVoiceover: AudioPlaybackState,
    val statePlayCry: AudioPlaybackState
)
