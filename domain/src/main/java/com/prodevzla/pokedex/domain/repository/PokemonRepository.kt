package com.prodevzla.pokedex.domain.repository

import com.prodevzla.pokedex.domain.model.GameVersionGroup
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.PokemonType
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<List<Pokemon>>

    fun getGameVersions(): Flow<List<GameVersionGroup>>

    fun getPokemonGenerations(): Flow<List<PokemonGeneration>>

    fun getPokemonTypes(): Flow<List<PokemonType>>

    fun getPokemonInfo(id: Int): Flow<PokemonInfo>

    fun getPokemon(id: Int): Flow<Pokemon>

    suspend fun updateSaveStatus(id: Int)

}
