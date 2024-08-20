package com.prodevzla.pokedex.model.domain

import com.prodevzla.pokedex.GetPokemonTypesQuery

data class Type(
    val id: Int,
    val name: String,
)

fun GetPokemonTypesQuery.Pokemon_v2_type.toDomain(): Type {
    return Type(
        id = this.id,
        name = this.name,
    )
}

fun List<GetPokemonTypesQuery.Pokemon_v2_type>.toDomain(): List<Type> {
    return this.map { it.toDomain() }
}
