package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.PokemonType

//should each filter has a lambda for the click operation?

class GetFiltersUseCase {

    operator fun invoke(
        pokemonGenerations: List<PokemonGeneration>, generationFilter: Int,
        pokemonTypes: List<PokemonType>, typeFilter: Int
    ): List<Filter> {
        return listOf(
            Filter.Version(
                weight = 2f,
            ),
            Filter.Generation(
                weight = 1f,
                pokemonGeneration = pokemonGenerations.first { it.id == generationFilter },
            ),
            Filter.Type(
                weight = 1f,
                pokemonType = pokemonTypes.first { it.id == typeFilter },
            )
        )
    }
}

sealed class Filter(open val weight: Float) {

    data class Version(override val weight: Float) : Filter(weight)

    data class Generation(
        override val weight: Float,
        val pokemonGeneration: PokemonGeneration
    ) : Filter(weight)

    data class Type(
        override val weight: Float,
        val pokemonType: PokemonType
    ) : Filter(weight)

    fun getLabel(): String {
        return when (this) {
            is Version -> "all game versions"
            is Generation -> this.pokemonGeneration.name//TODO there is something ODD here
            is Type -> this.pokemonType.name
        }
    }


}
