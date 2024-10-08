package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonInfoQuery
import com.prodevzla.pokedex.data.GetPokemonInfoQuery.Pokemon_v2_pokemonability
import com.prodevzla.pokedex.data.source.model.PokemonInfoEntity
import com.prodevzla.pokedex.domain.model.PokemonAbility
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
        cry = this.pokemon_v2_pokemoncries.toDomain(),
        abilities = this.pokemon_v2_pokemonabilities.toDomain()
    )
}

fun List<GetPokemonInfoQuery.Pokemon_v2_pokemon>.toDomain(): PokemonInfo {
    return this.map { it.toDomain() }.first()
}

fun List<Pokemon_v2_pokemonability>.toDomain(): List<PokemonAbility> {
    return this.map { it.pokemon_v2_ability!!.toDomain() }
}

fun GetPokemonInfoQuery.Pokemon_v2_ability.toDomain(): PokemonAbility {
    return PokemonAbility(
        id = this.id,
        name = this.pokemon_v2_abilitynames.first().name,
        description = this.pokemon_v2_abilityflavortexts.first().flavor_text,
        isHidden = this.pokemon_v2_pokemonabilities.first().is_hidden
    )
}

fun PokemonInfoEntity.fromEntityToDomain(): PokemonInfo {
    return PokemonInfo(
        height = this.height,
        weight = this.weight,
        genderRate = this.genderRate,
        flavorText = this.flavorText,
        cry = this.cries,
        abilities = this.abilities,
    )
}

fun PokemonInfo.toEntity(id: Int): PokemonInfoEntity {
    return PokemonInfoEntity(
        uid = id,
        height = this.height,
        weight = this.weight,
        genderRate = this.genderRate,
        flavorText = this.flavorText,
        cries = this.cry,
        abilities = this.abilities
    )
}