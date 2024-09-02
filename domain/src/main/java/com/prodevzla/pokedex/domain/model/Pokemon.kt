package com.prodevzla.pokedex.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    var image: String? = null,
    val types: List<PokemonType>,
    val generation: Int,
    //val gameVersions: List<Int>,
)
