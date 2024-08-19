package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.api.toPokemon
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // or @ActivityScoped?
class PokemonRepositoryImpl @Inject constructor(
    private val service: Service
) : PokemonRepository {

    override fun getPokemonList(
        limit: Int,
        offset: Int,
    ): Flow<Result<List<Pokemon>>> = flow {
        delay(700)
        emit(executeNetworkCall(
            networkCall = { service.getPokemonList(limit, offset) },
            processResponse = { body ->
                body?.results?.map { it.toPokemon() } ?: emptyList()
            },
        ))
    }

    override suspend fun getPokemonInfo(name: String): Result<Pokemon> {
        return Result.Success(
            Pokemon(
                id = 7040,
                name = "Agnes Farrell",
                image = null,
            ),
        )
    }

}
