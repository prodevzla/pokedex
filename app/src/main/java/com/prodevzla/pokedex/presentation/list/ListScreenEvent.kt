package com.prodevzla.pokedex.presentation.list

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CHANGED_SORTING
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_POKEMON
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_SEARCH
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_SORT
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Sort

//I could attach the tag value to each event to simplify further the viewModel logic
sealed class ListScreenEvent(val eventTag: String) {
    data class ClickFilter(val filter: Filter): ListScreenEvent("")
    data object ClickSort: ListScreenEvent(CLICK_SORT)
    data object ClickSearch: ListScreenEvent(CLICK_SEARCH)
    data class SelectFilter(val selection: Filterable): ListScreenEvent("")
    data class SelectSort(val selection: Sort): ListScreenEvent(CHANGED_SORTING)
    data class SearchPokemon(val input: String): ListScreenEvent("")
    data class ClickPokemon(val pokemon: Pokemon): ListScreenEvent(CLICK_POKEMON)
}

//remove the click lambda from the filter
//pass only one method onEvent to the ListScreen
