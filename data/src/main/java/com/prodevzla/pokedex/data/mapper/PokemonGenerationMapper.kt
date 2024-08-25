package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonGenerationsQuery
import com.prodevzla.pokedex.domain.model.PokemonGeneration

fun GetPokemonGenerationsQuery.Pokemon_v2_generation.toDomain(): PokemonGeneration {
    return PokemonGeneration(
        id = this.id,
        name = this.name,
    )
}

fun List<GetPokemonGenerationsQuery.Pokemon_v2_generation>.toDomain(): List<PokemonGeneration> {
    return this.map { it.toDomain()}
}
