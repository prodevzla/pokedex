package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class GetPokemonInfoUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(id: Int) = repository.getPokemonInfo(id).flowOn(Dispatchers.IO)

}
