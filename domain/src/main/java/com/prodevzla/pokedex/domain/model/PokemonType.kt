package com.prodevzla.pokedex.domain.model

import com.prodevzla.pokedex.data.GetPokemonTypesQuery


data class PokemonType(
    override val id: Int,
    override val name: String,
): Filterable

fun GetPokemonTypesQuery.Pokemon_v2_type.toDomain(): PokemonType {
    return PokemonType(
        id = this.id,
        name = this.name,
    )
}

fun List<GetPokemonTypesQuery.Pokemon_v2_type>.toDomain(): List<PokemonType> {
    return this.map { it.toDomain() }
}
