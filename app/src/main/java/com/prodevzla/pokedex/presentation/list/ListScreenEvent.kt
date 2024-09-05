package com.prodevzla.pokedex.presentation.list

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.FilterType
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CHANGED_FILTER_GENERATION
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CHANGED_FILTER_TYPE
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CHANGED_SORTING
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_FILTER_GEN
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_FILTER_TYPE
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_NAV_ICON
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_POKEMON
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_SEARCH
import com.prodevzla.pokedex.domain.model.FirebaseAnalytics.ListScreen.CLICK_SORT
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Sort

sealed class ListScreenEvent(val eventTag: String, val value: String? = null) {
    data class ClickFilter(val filter: Filter) : ListScreenEvent(
        eventTag = when (filter.type) {
            FilterType.GENERATION -> CLICK_FILTER_GEN
            FilterType.TYPE -> CLICK_FILTER_TYPE
        }
    )

    data object ClickSort : ListScreenEvent(CLICK_SORT)
    data object ClickSearch : ListScreenEvent(CLICK_SEARCH)
    data object ClickNavIcon : ListScreenEvent(CLICK_NAV_ICON)
    data class SelectFilter(val selection: Filterable, val filterType: FilterType) :
        ListScreenEvent(
            eventTag = when (filterType) {
                FilterType.GENERATION -> CHANGED_FILTER_GENERATION
                FilterType.TYPE -> CHANGED_FILTER_TYPE
            },
            value = selection.id.toString()
        )

    data class SelectSort(val selection: Sort) : ListScreenEvent(CHANGED_SORTING)
    data class SearchPokemon(val input: String) : ListScreenEvent("")
    data class ClickPokemon(val pokemon: Pokemon) :
        ListScreenEvent(
            eventTag = CLICK_POKEMON,
            value = "${pokemon.id}-${pokemon.name}"
        )
}
