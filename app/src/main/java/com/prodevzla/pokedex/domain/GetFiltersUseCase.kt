package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.model.domain.Filterable
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.PokemonType

//should each filter has a lambda for the click operation?

class GetFiltersUseCase {

    operator fun invoke(
        pokemonGenerations: List<PokemonGeneration>, generationFilter: Int,
        pokemonTypes: List<PokemonType>, typeFilter: Int
    ): List<Filter> {
        return listOf(
//            Filter.Version(
//                weight = 2f,
//            ),
            Filter.Generation(
                weight = 1f,
                selection = pokemonGenerations.first { it.id == generationFilter },
                values = pokemonGenerations
            ),
            Filter.Type(
                weight = 1f,
                selection = pokemonTypes.first { it.id == typeFilter },
                values = pokemonTypes
            )
        )
    }
}

sealed interface Filter {
    val weight: Float
    val selection: Filterable
    val values: List<Filterable>


//    data class Version(override val weight: Float) : Filter<PokemonGeneration>(weight)

    data class Generation(
        override val weight: Float,
        override val selection: PokemonGeneration,
        override val values: List<PokemonGeneration>
    ) : Filter

    data class Type(
        override val weight: Float,
        override val selection: PokemonType,
        override val values: List<PokemonType>
    ) : Filter


}
