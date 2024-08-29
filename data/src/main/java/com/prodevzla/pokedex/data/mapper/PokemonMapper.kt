package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonListQuery
import com.prodevzla.pokedex.data.source.model.PokemonEntity
import com.prodevzla.pokedex.domain.model.Pokemon

fun GetPokemonListQuery.Pokemon_v2_pokemon.toDomain(): Pokemon? {
    return Pokemon(
        id = this.id,
        name = this.name,
        //image = URL(this.dreamworld!!)
        types = this.pokemon_v2_pokemontypes.map { it.type_id!! },
        generation =
            this.pokemon_v2_pokemonforms
                .firstOrNull()
                ?.pokemon_v2_pokemonformgenerations
                ?.firstOrNull()
                ?.generation_id ?: return null,
        gameVersions =
            this.pokemon_v2_pokemonforms
                .firstOrNull()
                ?.pokemon_v2_pokemonformgenerations
                ?.firstOrNull()
                ?.pokemon_v2_generation
                ?.pokemon_v2_versiongroups
                ?.map { it.id }
                ?: emptyList()
    )
}

fun List<GetPokemonListQuery.Pokemon_v2_pokemon>.toDomain(): List<Pokemon> {
    return this.mapNotNull { it.toDomain() }
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        uid = this.id,
        name = this.name,
        types = this.types,
        generation = this.generation,
        gameVersions = this.gameVersions,
    )
}

fun List<Pokemon>.toEntities(): List<PokemonEntity> {
    return this.map { it.toEntity() }
}

fun PokemonEntity.fromEntityToDomain(): Pokemon {
    return Pokemon(
        id = this.uid,
        name = this.name,
        image = null,
        types = this.types,
        generation = this.generation,
        gameVersions = this.gameVersions
    )
}

fun List<PokemonEntity>.fromEntityToDomain() = this.map { it.fromEntityToDomain() }
