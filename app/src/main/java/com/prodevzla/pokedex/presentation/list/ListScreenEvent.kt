package com.prodevzla.pokedex.presentation.list

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Sort

//I could attach the tag value to each event to simplify further the viewModel logic
sealed interface ListScreenEvent {
    data class ClickFilter(val filter: Filter): ListScreenEvent
    data object ClickSort: ListScreenEvent
    data object ClickSearch: ListScreenEvent
    data class SelectFilter(val selection: Filterable): ListScreenEvent
    data class SelectSort(val selection: Sort): ListScreenEvent
    data class SearchPokemon(val input: String): ListScreenEvent
    data class ClickPokemon(val pokemon: Pokemon): ListScreenEvent
}

//remove the click lambda from the filter
//pass only one method onEvent to the ListScreen
