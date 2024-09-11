package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonInfoQuery
import com.prodevzla.pokedex.data.source.model.PokemonInfoEntity
import com.prodevzla.pokedex.domain.model.PokemonInfo


fun List<GetPokemonInfoQuery.Pokemon_v2_pokemoncry>.toDomain(): String {
    return (this.first().cries as LinkedHashMap<String, String>)["latest"]!!
}

fun GetPokemonInfoQuery.Pokemon_v2_pokemon.toDomain(): PokemonInfo {
    return PokemonInfo(
        height = this.height!!,
        weight = this.weight!!,
        genderRate = this.pokemon_v2_pokemonspecy?.gender_rate!!,
        flavorText = this.pokemon_v2_pokemonspecy.pokemon_v2_pokemonspeciesflavortexts.first().flavor_text,
        cries = this.pokemon_v2_pokemoncries.toDomain()
    )
}

fun List<GetPokemonInfoQuery.Pokemon_v2_pokemon>.toDomain(): PokemonInfo {
    return this.map { it.toDomain() }.first()
}

fun PokemonInfoEntity.fromEntityToDomain(): PokemonInfo {
    return PokemonInfo(
        height = this.height,
        weight = this.weight,
        genderRate = this.genderRate,
        flavorText = this.flavorText,
        cries = this.cries
    )
}

fun PokemonInfo.toEntity(id: Int): PokemonInfoEntity {
    return PokemonInfoEntity(
        uid = id,
        height = this.height,
        weight = this.weight,
        genderRate = this.genderRate,
        flavorText = this.flavorText,
        cries = this.cries
    )
}