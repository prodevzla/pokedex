package com.prodevzla.pokedex.presentation.favourites

import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface FavouritesScreenEvent {

    data object ClickBack: FavouritesScreenEvent

    data class ClickPokemon(val pokemon: Pokemon): FavouritesScreenEvent

    data class ToggleSave(val pokemon: Pokemon): FavouritesScreenEvent


}
