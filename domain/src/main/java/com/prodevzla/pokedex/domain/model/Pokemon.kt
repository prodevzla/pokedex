package com.prodevzla.pokedex.domain.model


data class Pokemon(
    val id: Int,
    val name: String,
    var image: String,
    val types: List<PokemonType>,
    val generation: Int,
    val isSaved: Boolean,
    //val gameVersions: List<Int>,
)

