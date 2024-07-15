package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int, offset: Int): Result<List<Pokemon>>

}
