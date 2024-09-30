package com.prodevzla.pokedex.domain.model

sealed interface StringDictionary {
    data object AllGenerations: StringDictionary
    data object AllTypes: StringDictionary
    data object DialogTitleGeneration: StringDictionary
    data object DialogTitleType: StringDictionary
}
