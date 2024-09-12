package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetPokemonInfoUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(id: Int): Flow<Result<PokemonInfo>> = repository.getPokemonInfo(id)
        .map { result ->
            when (result) {
                is Result.Success -> {
                    val updatedPokemonInfo = result.data.copy(
                        heightCm = "${result.data.height * 10}cm",
                        weightKg = "${result.data.weight / 10}Kg"
                    )
                    Result.Success(updatedPokemonInfo)
                }
                else -> result
            }
        }
        .flowOn(Dispatchers.IO)

}
