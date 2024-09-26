package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.R
import com.prodevzla.pokedex.domain.model.FilterDefault
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonGenerationsUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(): Flow<Result<List<Filterable>>> {
        val allTypesOption = FilterDefault(
            id = 0,
            name = UiText.StringResource(R.string.all_gens)
        )
        return repository.getPokemonGenerations()
            .map {
                listOf(allTypesOption) + it.map { generation: PokemonGeneration ->
                    generation.copy(
                        name = UiText.DynamicString(generation.name.value.replace("generation", "gen"))
                    )
                }
            }
            .asResult()
    }
}
