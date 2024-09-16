package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.R
import com.prodevzla.pokedex.domain.model.FilterDefault
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetPokemonTypesUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<Result<List<Filterable>>> {
        val allTypesOption = FilterDefault(
            id = 0,
            name = UiText.StringResource(R.string.all_types),
        )
        return repository.getPokemonTypes()
            .map<List<PokemonType>, Result<List<Filterable>>> {
                Result.Success(listOf(allTypesOption) + it)
            }
            .onStart { emit(Result.Loading) }
            .catch { e ->
                println(e)
                emit(Result.Error(e))
            }.flowOn(Dispatchers.IO)
    }
}
