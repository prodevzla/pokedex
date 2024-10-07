package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.repository.AbilityRepository

class GetAbilityUseCase(
    private val abilityRepository: AbilityRepository,
) {

    operator fun invoke(id: Int) = abilityRepository.getAbility(id)

}
