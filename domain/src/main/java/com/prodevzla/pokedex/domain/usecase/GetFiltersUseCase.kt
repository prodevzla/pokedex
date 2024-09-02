package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.R
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterOption
import com.prodevzla.pokedex.domain.model.Result
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
        onClickGeneration: (Int) -> Unit,
        onClickType: (Int) -> Unit,
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
                        dialogTitle = UiText.StringResource(R.string.dialog_title_generation),
                        weight = 1f,
                        selection = selectedGeneration,//generations.data.first { it.id == selectedGeneration },
                        values = generations.data,
                        onClickSelection = onClickGeneration,
                        filterOption = FilterOption.GENERATION
                    ),
                    Filter(
                        dialogTitle = UiText.StringResource(R.string.dialog_title_type),
                        weight = 1f,
                        selection = selectedType,//types.data.first { it.id == selectedType },
                        values = types.data,
                        onClickSelection = onClickType,
                        filterOption = FilterOption.TYPE
                    )
                )
            }

            else -> null
        }

    }
}
