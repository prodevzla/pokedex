package com.prodevzla.pokedex.model

inline fun <T> List<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): List<T> {
    return if (condition) {
        this.filter(predicate)
    } else {
        this
    }
}
