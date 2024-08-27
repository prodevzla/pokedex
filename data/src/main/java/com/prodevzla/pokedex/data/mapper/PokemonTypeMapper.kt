package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonTypesQuery
import com.prodevzla.pokedex.data.source.model.PokemonTypeEntity
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText

fun GetPokemonTypesQuery.Pokemon_v2_type.toDomain(): PokemonType {
    return PokemonType(
        id = this.id,
        name = UiText.DynamicString(this.name),
    )
}

fun List<GetPokemonTypesQuery.Pokemon_v2_type>.toDomain(): List<PokemonType> {
    return this.map { it.toDomain() }
}

fun PokemonType.toEntity(): PokemonTypeEntity {
    return PokemonTypeEntity(
        uid = this.id,
        name = this.name.value
    )
}

fun List<PokemonType>.toEntities(): List<PokemonTypeEntity> {
    return this.map { it.toEntity() }
}

fun PokemonTypeEntity.fromEntityToDomain(): PokemonType {
    return PokemonType(
        id = this.uid,
        name = UiText.DynamicString(this.name)
    )
}

fun List<PokemonTypeEntity>.fromEntityToDomain(): List<PokemonType> {
    return this.map { it.fromEntityToDomain() }
}
