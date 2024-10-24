package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.repository.AbilityRepository

class GetAbilitiesUseCase(
    private val abilityRepository: AbilityRepository
) {

    operator fun invoke() =
        abilityRepository.getAbilities().asResult()

}
