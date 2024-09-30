package com.prodevzla.pokedex.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prodevzla.pokedex.R
import com.prodevzla.pokedex.domain.model.StringDictionary
import com.prodevzla.pokedex.domain.model.UiText

fun StringDictionary.getRes(): Int {
    return when (this) {
        StringDictionary.AllGenerations -> R.string.all_gens
        StringDictionary.AllTypes -> R.string.all_types
        StringDictionary.DialogTitleGeneration -> R.string.dialog_title_generation
        StringDictionary.DialogTitleType -> R.string.dialog_title_type
    }
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringResource -> stringResource(resId, *args)
        is UiText.DomainResource -> stringResource(this.value.getRes())
    }
}
