package com.prodevzla.pokedex.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonType(
    override val id: Int,
    override val name: UiText.DynamicString,
): Filterable
