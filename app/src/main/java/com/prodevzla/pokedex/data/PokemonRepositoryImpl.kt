package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.DataError
import com.prodevzla.pokedex.model.Pokemon
import com.prodevzla.pokedex.model.Result
import com.prodevzla.pokedex.model.api.toPokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(private val service: Service) : PokemonRepository {

    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>, DataError.Network> {
        return executeNetworkCall(
            networkCall = { service.getPokemonList(limit, offset) },
            processResponse = { body ->
                body?.results?.map { it.toPokemon() } ?: emptyList()
            }
        )
    }

}
