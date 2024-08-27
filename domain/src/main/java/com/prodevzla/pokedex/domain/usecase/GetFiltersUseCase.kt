package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GetFiltersUseCase(
    private val getGameVersionsUseCase: GetGameVersionsUseCase,
    private val getPokemonGenerationsUseCase: GetPokemonGenerationsUseCase,
    private val getPokemonTypesUseCase: GetPokemonTypesUseCase,
) {

    data class GetFilterUseCaseData(
        val versions: List<Filterable>,
        val generations: List<Filterable>,
        val types: List<Filterable>,
    )

    operator fun invoke(
        versionFilter: MutableStateFlow<Int>,
        generationFilter: MutableStateFlow<Int>,
        typeFilter: MutableStateFlow<Int>,
        onClickVersion: (Int) -> Unit,
        onClickGeneration: (Int) -> Unit,
        onClickType: (Int) -> Unit,
    ): Flow<List<Filter>?> {
        val useCasesFlow: Flow<GetFilterUseCaseData?> = combine(
            getGameVersionsUseCase.invoke(),
            getPokemonGenerationsUseCase.invoke(),
            getPokemonTypesUseCase.invoke()
        ) { versions, generations, types ->
            when {
                versions is Result.Success && generations is Result.Success && types is Result.Success -> {
                    GetFilterUseCaseData(
                        versions = versions.data,
                        generations = generations.data,
                        types = types.data
                    )
                }

                else -> null
            }
        }
        return combine(
            useCasesFlow,
            versionFilter,
            generationFilter,
            typeFilter
        ) { data, filterVersion, filterGeneration, filterType ->
            data?.let {
                listOf(
                    Filter(
                        weight = 1f,
                        selection = data.versions.first { it.id == filterVersion },
                        values = data.versions,
                        onClickSelection = onClickVersion,
                    ),
                    Filter(
                        weight = 1f,
                        selection = data.generations.first { it.id == filterGeneration },
                        values = data.generations,
                        onClickSelection = onClickGeneration,
                    ),
                    Filter(
                        weight = 1f,
                        selection = data.types.first { it.id == filterType },
                        values = data.types,
                        onClickSelection = onClickType
                    )
                )
            }

        }
    }
}
