package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.FilterDefault
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetGameVersionsUseCase(
    private val repository: PokemonRepository
) {

//    operator fun invoke(): Flow<Result<List<Filterable>>> {
//        val allVersionsOption = FilterDefault(
//            id = 0,
//            name = UiText.StringResource(R.string.all_versions)
//        )
//
//        return repository.getGameVersions().map {
//            when (it) {
//                is Result.Success -> Result.Success(listOf(allVersionsOption) + it.data)
//                else -> it
//            }
//        }.flowOn(Dispatchers.IO)
//
//    }
}
