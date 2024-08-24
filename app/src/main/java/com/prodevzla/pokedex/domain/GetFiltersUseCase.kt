package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.model.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GetFiltersUseCase(
    private val getPokemonGenerationsUseCase: GetPokemonGenerationsUseCase,
    private val getPokemonTypesUseCase: GetPokemonTypesUseCase,
) {

    operator fun invoke(
        generationFilter: MutableStateFlow<Int>,
        typeFilter: MutableStateFlow<Int>,
        onClickGeneration: (Int) -> Unit,
        onClickType: (Int) -> Unit,
    ): Flow<List<Filter>?> = combine(
        getPokemonGenerationsUseCase.invoke(),
        getPokemonTypesUseCase.invoke(),
        generationFilter,
        typeFilter
    ) { generations, types, filterGeneration, filterType ->
        when {
            generations is Result.Success && types is Result.Success -> {
                listOf(
                    Filter.Generation(
                        weight = 1f,
                        selection = generations.data.first { it.id == filterGeneration },
                        values = generations.data,
                        onClickSelection = onClickGeneration,
                    ),
                    Filter.Type(
                        weight = 1f,
                        selection = types.data.first { it.id == filterType },
                        values = types.data,
                        onClickSelection = onClickType
                    )
                )
            }

            else -> null
        }


    }
}


