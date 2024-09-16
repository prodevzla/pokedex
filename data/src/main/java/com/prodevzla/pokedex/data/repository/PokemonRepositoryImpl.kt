package com.prodevzla.pokedex.data.repository

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.data.GetGameVersionGroupsQuery
import com.prodevzla.pokedex.data.GetPokemonGenerationsQuery
import com.prodevzla.pokedex.data.GetPokemonInfoQuery
import com.prodevzla.pokedex.data.GetPokemonListQuery
import com.prodevzla.pokedex.data.GetPokemonTypesQuery
import com.prodevzla.pokedex.data.mapper.executeApolloCall
import com.prodevzla.pokedex.data.mapper.fromEntityToDomain
import com.prodevzla.pokedex.data.mapper.toDomain
import com.prodevzla.pokedex.data.mapper.toEntities
import com.prodevzla.pokedex.data.mapper.toEntity
import com.prodevzla.pokedex.data.source.local.PokemonDao
import com.prodevzla.pokedex.data.source.local.PokemonGenerationDao
import com.prodevzla.pokedex.data.source.local.PokemonTypeDao
import com.prodevzla.pokedex.domain.model.GameVersionGroup
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class PokemonRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val pokemonDao: PokemonDao,
    private val pokemonGenerationDao: PokemonGenerationDao,
    private val pokemonTypeDao: PokemonTypeDao,
) : PokemonRepository {

    override fun getPokemonList(): Flow<List<Pokemon>> = flow {
//        delay(1200)
//        throw Exception("something")

        pokemonDao.getAll().takeIf { it.isNotEmpty() }
            ?.let {
                emit(it.fromEntityToDomain())
            } ?: run {
            emit(
                executeApolloCall(
                    query = {
                        apolloClient.query(GetPokemonListQuery())
                    },
                    processResponse = { body ->
                        val response: List<Pokemon> = body!!.pokemon_v2_pokemon.toDomain()
                        val entities = response.toEntities().toTypedArray()
                        pokemonDao.insertAll(*entities)
                        response
                    },
                )
            )
        }
    }

    override fun getGameVersions(): Flow<List<GameVersionGroup>> = flow {
        emit(
            executeApolloCall(
                query = {
                    apolloClient.query(GetGameVersionGroupsQuery())
                },
                processResponse = { body ->
                    val response: List<GameVersionGroup> = body!!.pokemon_v2_versiongroup.toDomain()
//                    val entities = response.toEntities().toTypedArray()
//                    pokemonDao.insertAll(*entities)
                    response
                },
            )
        )
    }

    override fun getPokemonGenerations(): Flow<List<PokemonGeneration>> = flow {
        pokemonGenerationDao.getAll().takeIf { it.isNotEmpty() }
            ?.let {
                emit(it.fromEntityToDomain())
            } ?: run {
            emit(
                executeApolloCall(
                    query = {
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
    }

    override fun getPokemonTypes(): Flow<List<PokemonType>> = flow {
        pokemonTypeDao.getAll().takeIf { it.isNotEmpty() }
            ?.let {
                emit(it.fromEntityToDomain())
            } ?: run {
            emit(
                executeApolloCall(
                    query = {
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

    override fun getPokemonInfo(id: Int): Flow<PokemonInfo> = flow {
        //delay(1500)
        pokemonDao.getPokemonInfo(id)?.let {
            emit(it.fromEntityToDomain())
            return@flow
        } ?: emit(
            executeApolloCall(
                query = {
                    apolloClient.query(GetPokemonInfoQuery(id))
                },
                processResponse = { body ->
                    val response = body!!.pokemon_v2_pokemon.toDomain()
                    val entity = response.toEntity(id)
                    pokemonDao.insertPokemonInfo(entity)
                    response
                }
            )
        )
    }

}
