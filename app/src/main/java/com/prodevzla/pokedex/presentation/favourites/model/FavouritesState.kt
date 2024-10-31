package com.prodevzla.pokedex.presentation.favourites.model

import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface FavouritesState {

    data object Loading : FavouritesState

    data class Content(
        val pokemonList: List<Pokemon>,
    ) : FavouritesState

    data object Error : FavouritesState
}
