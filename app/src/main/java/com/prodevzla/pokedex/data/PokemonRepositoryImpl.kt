package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.Pokemon
import com.prodevzla.pokedex.model.Result
import com.prodevzla.pokedex.model.api.toPokemon
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton //or @ActivityScoped?
class PokemonRepositoryImpl @Inject constructor(
    private val service: Service
) : PokemonRepository {

    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>> {
        delay(3000)
        return executeNetworkCall(
            networkCall = { service.getPokemonList(limit, offset) },
            processResponse = { body ->
                body?.results?.map { it.toPokemon() } ?: emptyList()
            }
        )
    }

    override suspend fun getPokemonInfo(name: String): Result<Pokemon> {
        return Result.Success(
            Pokemon(
                id = 7040,
                name = "Agnes Farrell",
                image = null
            )
        )
    }

}
