package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.repository.PokemonRepository

class ToggleSavePokemonUseCase(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(id: Int) = repository.updateSaveStatus(id)

}
