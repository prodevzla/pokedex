package com.prodevzla.pokedex.data.repository

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.data.GetAbilityQuery
import com.prodevzla.pokedex.data.mapper.executeApolloCall
import com.prodevzla.pokedex.data.mapper.toDomain
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.repository.AbilityRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AbilityRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AbilityRepository {

    override fun getAbility(id: Int): Flow<Ability> = flow {
        executeApolloCall(
            query = {
                apolloClient.query(GetAbilityQuery(id))
            },
            processResponse = { body ->
                delay(1500)
                emit(body!!.pokemon_v2_ability.toDomain())
            }
        )
    }

}
