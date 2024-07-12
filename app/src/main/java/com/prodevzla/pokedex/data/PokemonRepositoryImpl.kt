package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.Pokemon
import com.prodevzla.pokedex.model.api.toPokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(private val service: Service): PokemonRepository {

    override suspend fun getPokemonList(): Result<List<Pokemon>> {
        try {
            val response = service.getPokemonList()

            return Result.success(response.body()?.results?.map {
                it.toPokemon()
            }) as Result<List<Pokemon>>
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.success(emptyList())

        }

    }

}
