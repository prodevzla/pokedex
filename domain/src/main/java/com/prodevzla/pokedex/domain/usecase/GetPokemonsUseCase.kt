package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetPokemonsUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(): Flow<Result<List<Pokemon>>> =
        repository.getPokemonList()
            .asResult()

}
