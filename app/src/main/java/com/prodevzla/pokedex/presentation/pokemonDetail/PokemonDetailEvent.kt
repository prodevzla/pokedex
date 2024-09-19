package com.prodevzla.pokedex.presentation.pokemonDetail

sealed interface PokemonDetailEvent {
    data object SaveClick: PokemonDetailEvent
}
