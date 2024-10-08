package com.prodevzla.pokedex.domain.model

data class Ability(
    val id: Int,
    val name: String,
    val flavorText: String,
    val shortEffect: String,
    val longEffect: String,
)
