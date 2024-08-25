package com.prodevzla.pokedex.domain.model

data class PokemonType(
    override val id: Int,
    override val name: String,
): Filterable
