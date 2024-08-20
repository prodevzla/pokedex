package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import com.prodevzla.pokedex.model.domain.Type
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<Result<List<Pokemon>>>

    fun getPokemonTypes(): Flow<Result<List<Type>>>

}
