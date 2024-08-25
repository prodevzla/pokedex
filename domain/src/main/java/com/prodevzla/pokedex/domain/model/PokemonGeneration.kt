package com.prodevzla.pokedex.domain.model

import com.prodevzla.pokedex.data.GetPokemonGenerationsQuery


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
