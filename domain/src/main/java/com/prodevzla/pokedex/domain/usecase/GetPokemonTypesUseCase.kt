package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.FilterDefault
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.StringDictionary
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonTypesUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<Result<List<Filterable>>> {
        val allTypesOption = FilterDefault(
            id = 0,
            name = UiText.DomainResource(StringDictionary.AllTypes),
        )
        return repository.getPokemonTypes()
            .map {
                listOf(allTypesOption) + it
            }
            .asResult()
    }
}
