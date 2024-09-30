package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonInfoUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(id: Int): Flow<Result<PokemonInfo>> =
        repository.getPokemonInfo(id).asResult()

}
