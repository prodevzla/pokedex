package com.prodevzla.pokedex.domain.model

data class PokemonInfo(
    val height: Int,
    val weight: Int,
    val genderRate: Int,
    val flavorText: String,
    val cries: String,
    var heightCm: String = "0 cm",
    var weightKg: String = "0 Kg"
)
