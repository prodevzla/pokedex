package com.prodevzla.pokedex.domain.model

//sealed interface Filter {
//    val weight: Float
//    val selection: Filterable
//    val values: List<Filterable>
//    val onClickSelection: (Int) -> Unit
//
//
////    data class Version(override val weight: Float) : Filter<PokemonGeneration>(weight)
//
//    data class Generation(
//        override val weight: Float,
//        override val selection: Filterable,
//        override val values: List<Filterable>,
//        override val onClickSelection: (Int) -> Unit
//    ) : Filter
//
//    data class Type(
//        override val weight: Float,
//        override val selection: Filterable,
//        override val values: List<Filterable>,
//        override val onClickSelection: (Int) -> Unit
//    ) : Filter
//
//
//}

data class Filter(
    val dialogTitle: UiText,
    val weight: Float,
    val selection: Int,
    val values: List<Filterable>,
    val type: FilterType
) {
    val selectedItem: Filterable
        get() = values.first { it.id == selection }
}

enum class FilterType {
    GENERATION, TYPE
}
