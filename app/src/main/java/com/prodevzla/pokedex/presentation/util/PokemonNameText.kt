package com.prodevzla.pokedex.presentation.util

import com.prodevzla.pokedex.domain.model.Pokemon

fun Pokemon.toTitle(): String {
    return "${getUIId()} ${getUIName()}"
}

fun Pokemon.getUIId(): String {
    return "#${
        this.id.toString().padStart(4, '0')
    }"
}

fun Pokemon.getUIName(): String {
    return this.name.replaceFirstChar { it.uppercase() }
}
