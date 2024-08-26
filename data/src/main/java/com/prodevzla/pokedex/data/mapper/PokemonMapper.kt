package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonListQuery
import com.prodevzla.pokedex.data.source.model.PokemonEntity
import com.prodevzla.pokedex.domain.model.Pokemon

fun GetPokemonListQuery.Pokemon_v2_pokemon.toDomain(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        //image = URL(this.dreamworld!!)
        types = this.pokemon_v2_pokemontypes.map { it.type_id!! },
        generation = this.pokemon_v2_pokemonforms.firstOrNull()?.pokemon_v2_pokemonformgenerations?.firstOrNull()?.generation_id
    )
}

fun List<GetPokemonListQuery.Pokemon_v2_pokemon>.toDomain(): List<Pokemon> {
    return this.map { it.toDomain() }
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        uid = this.id,
        name = this.name,
        types = this.types.first(),
        generation = this.generation
    )
}

fun List<Pokemon>.toEntities(): List<PokemonEntity> {
    return this.map { it.toEntity() }
}