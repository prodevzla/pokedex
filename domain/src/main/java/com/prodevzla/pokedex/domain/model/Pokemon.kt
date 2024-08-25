package com.prodevzla.pokedex.domain.model

import com.prodevzla.pokedex.data.GetPokemonListQuery
import java.net.URL

data class Pokemon(
    val id: Int,
    val name: String,
    var image: URL? = null,
    val types: List<Int>,
    val generation: Int?,
)

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
