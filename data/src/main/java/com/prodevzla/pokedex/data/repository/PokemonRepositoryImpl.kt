package com.prodevzla.pokedex.data.repository

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.data.GetPokemonGenerationsQuery
import com.prodevzla.pokedex.data.GetPokemonListQuery
import com.prodevzla.pokedex.data.GetPokemonTypesQuery
import com.prodevzla.pokedex.data.mapper.executeApolloCall
import com.prodevzla.pokedex.data.mapper.fromEntityToDomain
import com.prodevzla.pokedex.data.mapper.toDomain
import com.prodevzla.pokedex.data.mapper.toEntities
import com.prodevzla.pokedex.data.source.local.PokemonDao
import com.prodevzla.pokedex.data.source.local.PokemonGenerationDao
import com.prodevzla.pokedex.data.source.local.PokemonTypeDao
import com.prodevzla.pokedex.data.source.model.PokemonTypeEntity
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val pokemonDao: PokemonDao,
    private val pokemonGenerationDao: PokemonGenerationDao,
    private val pokemonTypeDao: PokemonTypeDao,
) : PokemonRepository {

    override fun getPokemonList(): Flow<Result<List<Pokemon>>> = flow {
        if (pokemonDao.getAll().isEmpty()) {
            emit(
                executeApolloCall(
                    networkCall = {
                        apolloClient.query(GetPokemonListQuery())
                    },
                    processResponse = { body ->
                        val response: List<Pokemon> = body!!.pokemon_v2_pokemon.toDomain()
                        val entities = response.toEntities().toTypedArray()
                        println(entities)
                        //pokemonDao.insertAll()
                        response
                    },
                )
            )
        }

    }

    override fun getPokemonGenerations(): Flow<Result<List<PokemonGeneration>>> = flow {
        val generations = pokemonGenerationDao.getAll()
        if (generations.isNotEmpty()) {
            emit(Result.Success(generations.fromEntityToDomain()))
            return@flow
        }
        emit(
            executeApolloCall(
                networkCall = {
                    apolloClient.query(GetPokemonGenerationsQuery())
                },
                processResponse = { body ->
                    val response = body!!.pokemon_v2_generation.toDomain()
                    val entities = response.toEntities().toTypedArray()
                    pokemonGenerationDao.insertAll(*entities)
                    response
                }
            )
        )
    }

    override fun getPokemonTypes(): Flow<Result<List<PokemonType>>> = flow {
        val types = pokemonTypeDao.getAll()
        if (types.isNotEmpty()) {
            emit(Result.Success(types.fromEntityToDomain()))
            return@flow
        }

        emit(
            executeApolloCall(
                networkCall = {
                    apolloClient.query(GetPokemonTypesQuery())
                },
                processResponse = { body ->
                    val response: List<PokemonType> = body!!.pokemon_v2_type.toDomain()
                    val entities = response.toEntities().toTypedArray()
                    pokemonTypeDao.insertAll(*entities)
                    response
                }
            )
        )
    }

}
