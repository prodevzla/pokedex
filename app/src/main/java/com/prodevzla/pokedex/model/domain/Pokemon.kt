package com.prodevzla.pokedex.model.domain

import com.prodevzla.pokedex.GetPokemonListQuery
import java.net.URL

data class Pokemon(val id: Int, val name: String, val image: URL? = null)

fun GetPokemonListQuery.Pokemon_v2_pokemon.toDomain(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        //image = URL(this.dreamworld!!)
    )
}

fun List<GetPokemonListQuery.Pokemon_v2_pokemon>.toDomain(): List<Pokemon> {
    return this.map { it.toDomain() }
}
