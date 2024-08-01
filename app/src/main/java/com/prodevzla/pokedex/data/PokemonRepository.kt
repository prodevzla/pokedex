package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>>

    suspend fun getPokemonInfo(
        name: String
    ): Result<Pokemon>

}
