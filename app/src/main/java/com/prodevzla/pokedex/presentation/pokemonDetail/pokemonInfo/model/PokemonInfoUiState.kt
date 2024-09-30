package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.model

import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.PokemonType

sealed interface PokemonInfoUiState {
    data object Loading : PokemonInfoUiState
    data object Error : PokemonInfoUiState
    data class Content(
        val spec: PokemonSpec,
        val abilities: List<Ability>,
        val pokemonType: PokemonType,
    ) : PokemonInfoUiState
}
