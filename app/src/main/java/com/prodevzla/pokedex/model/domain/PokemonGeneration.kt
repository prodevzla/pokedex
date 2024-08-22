package com.prodevzla.pokedex.model.domain

import com.prodevzla.pokedex.GetPokemonGenerationsQuery

data class PokemonGeneration(
    override val id: Int,
    override val name: String
): Filterable

fun GetPokemonGenerationsQuery.Pokemon_v2_generation.toDomain(): PokemonGeneration {
    return PokemonGeneration(
        id = this.id,
        name = this.name,
    )
}

fun List<GetPokemonGenerationsQuery.Pokemon_v2_generation>.toDomain(): List<PokemonGeneration> {
    return this.map { it.toDomain()}
}
