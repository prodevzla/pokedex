package com.prodevzla.pokedex.domain.model

data class PokemonInfo(
    val height: Int,
    val weight: Int,
    val genderRate: Int,
    val flavorText: String,
    val cry: String,
    val abilities: List<PokemonAbility>
)
