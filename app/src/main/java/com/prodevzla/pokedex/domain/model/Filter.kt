package com.prodevzla.pokedex.domain.model

import com.prodevzla.pokedex.model.domain.Filterable
import com.prodevzla.pokedex.model.domain.PokemonGeneration
import com.prodevzla.pokedex.model.domain.PokemonType

sealed interface Filter {
    val weight: Float
    val selection: Filterable
    val values: List<Filterable>
    val onClickSelection: (Int) -> Unit


//    data class Version(override val weight: Float) : Filter<PokemonGeneration>(weight)

    data class Generation(
        override val weight: Float,
        override val selection: PokemonGeneration,
        override val values: List<PokemonGeneration>,
        override val onClickSelection: (Int) -> Unit
    ) : Filter

    data class Type(
        override val weight: Float,
        override val selection: PokemonType,
        override val values: List<PokemonType>,
        override val onClickSelection: (Int) -> Unit
    ) : Filter


}
