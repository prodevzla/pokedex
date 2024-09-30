package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.model.StringDictionary
import com.prodevzla.pokedex.domain.model.UiText
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
    ): Flow<List<Filter>?> = combine(
        getPokemonGenerationsUseCase.invoke(),
        getPokemonTypesUseCase.invoke(),
        generationFilter,
        typeFilter
    ) { generations, types, selectedGeneration, selectedType ->
        when {
            generations is Result.Success && types is Result.Success -> {
                listOf(
//                    Filter(
//                        title = UiText.StringResource(R.string.dialog_title_versions),
//                        weight = 1.5f,
//                        selection = data.versions.first { it.id == filterVersion },
//                        values = data.versions,
//                        onClickSelection = onClickVersion,
//                        filterOption = FilterOption.VERSION
//                    ),
                    Filter(
                        dialogTitle = UiText.DomainResource(StringDictionary.DialogTitleGeneration),
                        weight = 1f,
                        selection = selectedGeneration,//generations.data.first { it.id == selectedGeneration },
                        values = generations.data,
                        type = FilterType.GENERATION
                    ),
                    Filter(
                        dialogTitle = UiText.DomainResource(StringDictionary.DialogTitleType),
                        weight = 1f,
                        selection = selectedType,//types.data.first { it.id == selectedType },
                        values = types.data,
                        type = FilterType.TYPE
                    )
                )
            }

            else -> null
        }

    }
}
