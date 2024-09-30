package com.prodevzla.pokedex.presentation.list.model

import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Sort

/**
 * Represents the different states of the Pokémon list screen.
 */
sealed interface ListState {

    /**
     * Represents the loading state when data is being fetched.
     */
    data object Loading : ListState

    /**
     * Represents the content state when data has been successfully fetched.
     *
     * @property pokemonList The list of Pokémon to display. This list is always present if the state is Content.
     * @property filters The list of filters to apply to the Pokémon list. If the app fails to fetch generations and/or types,
     * the filters will be null, and the Pokémon, if fetched successfully, will still appear.
     */
    data class Content(
        val pokemonList: List<Pokemon>,
        val filters: List<Filter>?,
        val sort: Sort,
        val search: String,
    ) : ListState

    /**
     * Represents the error state when data fails to load.
     */
    data object Error : ListState
}
