package com.prodevzla.pokedex.domain.model

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
sealed class UiText {

    @Serializable
    data class DynamicString(val value: String): UiText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ): UiText()

}
