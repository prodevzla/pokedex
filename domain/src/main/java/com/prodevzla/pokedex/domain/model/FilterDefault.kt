package com.prodevzla.pokedex.domain.model

data class FilterDefault(
    override val id: Int,
    override val name: UiText.StringResource,
): Filterable
