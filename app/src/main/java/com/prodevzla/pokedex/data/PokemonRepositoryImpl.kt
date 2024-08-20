package com.prodevzla.pokedex.data

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.GetPokemonListQuery
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import com.prodevzla.pokedex.model.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // or @ActivityScoped?
class PokemonRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : PokemonRepository {

    override fun getPokemonList(): Flow<Result<List<Pokemon>>> = flow {
        emit(executeApolloCall(
            networkCall = { apolloClient.query(GetPokemonListQuery()) },
            processResponse = { body ->
                body!!.pokemon_v2_pokemon.toDomain()
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
