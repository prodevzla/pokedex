package com.prodevzla.pokedex.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prodevzla.pokedex.domain.model.UiText

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringResource -> stringResource(resId, *args)
    }
}
