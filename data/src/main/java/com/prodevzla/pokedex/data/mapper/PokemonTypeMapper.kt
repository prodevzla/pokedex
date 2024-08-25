package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonTypesQuery
import com.prodevzla.pokedex.domain.model.PokemonType

fun GetPokemonTypesQuery.Pokemon_v2_type.toDomain(): PokemonType {
    return PokemonType(
        id = this.id,
        name = this.name,
    )
}

fun List<GetPokemonTypesQuery.Pokemon_v2_type>.toDomain(): List<PokemonType> {
    return this.map { it.toDomain() }
}
