package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.Result
import com.prodevzla.pokedex.model.domain.PokemonType
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<Result<List<Pokemon>>>

    fun getPokemonGenerations(): Flow<Result<List<PokemonGeneration>>>

    fun getPokemonTypes(): Flow<Result<List<PokemonType>>>

}
