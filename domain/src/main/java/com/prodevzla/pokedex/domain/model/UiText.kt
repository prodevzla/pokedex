package com.prodevzla.pokedex.domain.model

sealed class UiText {

    data class DynamicString(val value: String): UiText()

    class StringResource(
        val resId: Int,
        vararg val args: Any,
    ): UiText()

    data class DomainResource(val value: StringDictionary): UiText()

}
