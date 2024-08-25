package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.model.domain.Result
import com.prodevzla.pokedex.domain.model.PokemonType
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<Result<List<Pokemon>>>

    fun getPokemonGenerations(): Flow<Result<List<PokemonGeneration>>>

    fun getPokemonTypes(): Flow<Result<List<PokemonType>>>

}
