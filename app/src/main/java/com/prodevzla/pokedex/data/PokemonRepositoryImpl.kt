package com.prodevzla.pokedex.data

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.GetPokemonGenerationsQuery
import com.prodevzla.pokedex.GetPokemonListQuery
import com.prodevzla.pokedex.GetPokemonTypesQuery
import com.prodevzla.pokedex.domain.PokemonRepository
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.Result
import com.prodevzla.pokedex.model.domain.PokemonType
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

    override fun getPokemonGenerations(): Flow<Result<List<PokemonGeneration>>> = flow {
        emit(
            executeApolloCall(
                networkCall = {
                    apolloClient.query(GetPokemonGenerationsQuery())
                },
                processResponse = { body ->
                    body!!.pokemon_v2_generation.toDomain()
                }
            )
        )
    }

    override fun getPokemonTypes(): Flow<Result<List<PokemonType>>> = flow {
        emit(
            executeApolloCall(
                networkCall = {
                    apolloClient.query(GetPokemonTypesQuery())
                },
                processResponse = { body ->
                    body!!.pokemon_v2_type.toDomain()
                }
            )
        )
    }

}
