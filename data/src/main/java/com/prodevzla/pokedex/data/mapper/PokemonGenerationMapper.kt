package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonGenerationsQuery
import com.prodevzla.pokedex.data.source.model.PokemonGenerationEntity
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

fun PokemonGeneration.toEntity(): PokemonGenerationEntity {
    return PokemonGenerationEntity(
        uid = this.id,
        name = this.name
    )
}

fun List<PokemonGeneration>.toEntities() = this.map { it.toEntity() }

fun PokemonGenerationEntity.fromEntityToDomain(): PokemonGeneration {
    return PokemonGeneration(
        id = this.uid,
        name = this.name
    )
}

fun List<PokemonGenerationEntity>.fromEntityToDomain() = this.map { it.fromEntityToDomain() }
