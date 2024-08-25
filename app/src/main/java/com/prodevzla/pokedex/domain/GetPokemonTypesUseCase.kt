package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import com.prodevzla.pokedex.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonTypesUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<Result<List<PokemonType>>> {
        val allTypesOption = PokemonType(
            id = 0,
            name = "all types"
        )
        return repository.getPokemonTypes().map {
            when(it) {
                is Result.Error -> it
                is Result.Success -> Result.Success(listOf(allTypesOption) + it.data)
            }
        }
    }
}
