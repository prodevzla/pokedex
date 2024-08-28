package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.R
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterOption
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.UiText
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
                        title = UiText.StringResource(R.string.dialog_title_versions),
                        weight = 1.5f,
                        selection = data.versions.first { it.id == filterVersion },
                        values = data.versions,
                        onClickSelection = onClickVersion,
                        filterOption = FilterOption.VERSION
                    ),
                    Filter(
                        title = UiText.StringResource(R.string.dialog_title_generation),
                        weight = 1f,
                        selection = data.generations.first { it.id == filterGeneration },
                        values = data.generations,
                        onClickSelection = onClickGeneration,
                        filterOption = FilterOption.GENERATION
                    ),
                    Filter(
                        title = UiText.StringResource(R.string.dialog_title_type),
                        weight = 1f,
                        selection = data.types.first { it.id == filterType },
                        values = data.types,
                        onClickSelection = onClickType,
                        filterOption = FilterOption.TYPE
                    )
                )
            }

        }
    }
}
