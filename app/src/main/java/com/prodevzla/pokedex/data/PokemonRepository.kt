package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<Result<List<Pokemon>>>

}
