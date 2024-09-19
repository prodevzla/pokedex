package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class GetPokemonUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(id: Int): Flow<Pokemon> =
        repository.getPokemon(id)
            .catch { e ->
                println(e)
            }.flowOn(Dispatchers.IO)

}
