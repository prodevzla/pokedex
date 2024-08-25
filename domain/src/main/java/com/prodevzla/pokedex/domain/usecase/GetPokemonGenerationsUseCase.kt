package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import com.prodevzla.pokedex.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonGenerationsUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(): Flow<Result<List<PokemonGeneration>>> {
        val allTypesOption = PokemonGeneration(
            id = 0,
            name = "all gens"
        )
        return repository.getPokemonGenerations().map {
            when (it) {
                is Result.Error -> it
                is Result.Success -> Result.Success(listOf(allTypesOption) + it.data.map { generation ->
                    PokemonGeneration(
                        generation.id, generation.name.replace("generation", "gen")
                    )
                })
            }
        }
    }

}
