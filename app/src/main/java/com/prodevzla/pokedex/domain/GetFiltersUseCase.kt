package com.prodevzla.pokedex.domain

import androidx.compose.ui.graphics.Color
import com.prodevzla.pokedex.model.domain.PokemonType
import com.prodevzla.pokedex.presentation.list.getColor
import com.prodevzla.pokedex.ui.theme.NeutralGrey

class GetFiltersUseCase {

    operator fun invoke(pokemonTypes: List<PokemonType>, typeFilter: Int): List<Filter> {
        return listOf(
            Filter(
                label = "all game versions",
                type = FilterType.VERSIONS,
                weight = 2f,
                color = NeutralGrey
            ),
            Filter(
                label = "all gens",
                type = FilterType.GENERATIONS,
                weight = 1f,
                color = NeutralGrey
            ),
            Filter(
                label = pokemonTypes.first { it.id == typeFilter }.name,
                type = FilterType.TYPES,
                weight = 1f,
                color = pokemonTypes.first { it.id == typeFilter }.getColor()
            )
        )
    }
}

data class Filter(
    val label: String,
    val type: FilterType,
    val weight: Float,
    val color: Color,
)

enum class FilterType {
    VERSIONS,
    GENERATIONS,
    TYPES,
}
