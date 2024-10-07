package com.prodevzla.pokedex.data.repository

import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.repository.AbilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AbilityRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AbilityRepository {

    override fun getAbility(id: Int): Flow<Ability> {
        return flowOf(
            Ability(
                name = "August Sanford", description = "convallis", isHidden = false
            )
        )
    }

}
