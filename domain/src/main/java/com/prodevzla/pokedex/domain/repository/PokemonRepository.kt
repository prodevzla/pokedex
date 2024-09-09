package com.prodevzla.pokedex.domain.repository

import com.prodevzla.pokedex.domain.model.GameVersionGroup
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonDetails
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<Result<List<Pokemon>>>

    fun getGameVersions(): Flow<Result<List<GameVersionGroup>>>

    fun getPokemonGenerations(): Flow<Result<List<PokemonGeneration>>>

    fun getPokemonTypes(): Flow<Result<List<PokemonType>>>

    fun getPokemonDetails(id: Int): Flow<Result<PokemonDetails>>

}
