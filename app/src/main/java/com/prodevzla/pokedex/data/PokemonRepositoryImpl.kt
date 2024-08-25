package com.prodevzla.pokedex.data

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : PokemonRepository {

    override fun getPokemonList(): Flow<Result<List<Pokemon>>> = flow {
        emit(executeApolloCall(
            networkCall = {
                apolloClient.query(GetPokemonListQuery())
            },
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
