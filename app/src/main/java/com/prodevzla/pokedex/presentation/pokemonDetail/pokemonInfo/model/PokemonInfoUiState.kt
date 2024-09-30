package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model

sealed interface PokemonInfoUiState {
    data object Loading : PokemonInfoUiState
    data object Error : PokemonInfoUiState
    data class Content(
        val spec: PokemonSpec,
        val abilities: List<String>,
    ) : PokemonInfoUiState
}
