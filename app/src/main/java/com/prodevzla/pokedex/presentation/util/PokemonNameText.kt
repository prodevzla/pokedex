package com.prodevzla.pokedex.presentation.util

import com.prodevzla.pokedex.domain.model.Pokemon

fun Pokemon.toTitle(): String {
    return "#${
        this.id.toString().padStart(4, '0')
    } ${ this.name.replaceFirstChar { it.uppercase() }}"
}