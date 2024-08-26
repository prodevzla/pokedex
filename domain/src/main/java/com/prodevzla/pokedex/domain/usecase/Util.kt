package com.prodevzla.pokedex.domain.usecase

inline fun <T> List<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): List<T> {
    return if (condition) {
        this.filter(predicate)
    } else {
        this
    }
}
