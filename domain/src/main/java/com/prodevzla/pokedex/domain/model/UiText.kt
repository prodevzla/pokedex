package com.prodevzla.pokedex.domain.model

import androidx.annotation.StringRes

sealed class UiText {

    data class DynamicString(val value: String): UiText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ): UiText()

}
