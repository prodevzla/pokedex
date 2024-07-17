package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.DataError
import com.prodevzla.pokedex.model.Pokemon
import com.prodevzla.pokedex.model.Result

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>, DataError.Network>

}
