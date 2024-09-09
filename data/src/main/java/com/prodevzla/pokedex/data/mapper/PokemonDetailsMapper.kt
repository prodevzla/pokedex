package com.prodevzla.pokedex.data.mapper

import com.prodevzla.pokedex.data.GetPokemonDetailsQuery
import com.prodevzla.pokedex.domain.model.PokemonDetails


fun List<GetPokemonDetailsQuery.Pokemon_v2_pokemoncry>.toDomain(): String {
    return (this.first().cries as LinkedHashMap<String, String>)["latest"]!!
}

fun GetPokemonDetailsQuery.Pokemon_v2_pokemon.toDomain(): PokemonDetails {
    return PokemonDetails(
        height = this.height!!,
        wight = this.weight!!,
        genderRate = this.pokemon_v2_pokemonspecy?.gender_rate!!,
        flavorText = this.pokemon_v2_pokemonspecy.pokemon_v2_pokemonspeciesflavortexts.first().flavor_text,
        cries = this.pokemon_v2_pokemoncries.toDomain()
    )
}

fun List<GetPokemonDetailsQuery.Pokemon_v2_pokemon>.toDomain(): PokemonDetails {
    return this.map { it.toDomain() }.first()
}
