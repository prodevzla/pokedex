package com.prodevzla.pokedex.domain.model

data class PokemonGeneration(
    override val id: Int,
    override val name: UiText.DynamicString,
): Filterable
