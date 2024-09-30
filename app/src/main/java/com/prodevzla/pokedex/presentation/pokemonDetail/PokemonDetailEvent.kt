package com.prodevzla.pokedex.presentation.pokemonDetail

sealed interface PokemonDetailEvent {
    data object OnClickBack: PokemonDetailEvent
    data object SaveClick: PokemonDetailEvent
}
