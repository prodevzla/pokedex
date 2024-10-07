package com.prodevzla.pokedex.domain.repository

import com.prodevzla.pokedex.domain.model.Ability
import kotlinx.coroutines.flow.Flow

interface AbilityRepository {

    fun getAbility(id: Int): Flow<Ability>

}
