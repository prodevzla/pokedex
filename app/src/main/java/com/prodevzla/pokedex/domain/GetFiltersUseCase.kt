package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.model.domain.PokemonType

class GetFiltersUseCase {

    operator fun invoke(pokemonTypes: List<PokemonType>, typeFilter: Int): List<Filter> {
        return listOf(
            Filter(
                label = "all game versions",
                type = FilterType.VERSIONS,
                weight = 2f,
            ),
            Filter(
                label = "all gens",
                type = FilterType.GENERATIONS,
                weight = 1f,
            ),
            Filter(
                label = pokemonTypes.firstOrNull { it.id == typeFilter }?.name ?: "all types",
                type = FilterType.TYPES,
                weight = 1f
            )
        )
    }
}

data class Filter(
    val label: String,
    val type: FilterType,
    val weight: Float,
)

enum class FilterType {
    VERSIONS,
    GENERATIONS,
    TYPES,
}
