package com.prodevzla.pokedex.presentation.util

import androidx.compose.ui.graphics.Color
import com.prodevzla.pokedex.domain.model.Filter
import com.prodevzla.pokedex.domain.model.Filterable
import com.prodevzla.pokedex.domain.model.PokemonGeneration
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.ui.theme.NeutralGrey
import com.prodevzla.pokedex.ui.theme.SelectedGrey
import com.prodevzla.pokedex.ui.theme.typeBug
import com.prodevzla.pokedex.ui.theme.typeDark
import com.prodevzla.pokedex.ui.theme.typeDragon
import com.prodevzla.pokedex.ui.theme.typeElectric
import com.prodevzla.pokedex.ui.theme.typeFairy
import com.prodevzla.pokedex.ui.theme.typeFighting
import com.prodevzla.pokedex.ui.theme.typeFire
import com.prodevzla.pokedex.ui.theme.typeFlying
import com.prodevzla.pokedex.ui.theme.typeGhost
import com.prodevzla.pokedex.ui.theme.typeGrass
import com.prodevzla.pokedex.ui.theme.typeGround
import com.prodevzla.pokedex.ui.theme.typeIce
import com.prodevzla.pokedex.ui.theme.typeNormal
import com.prodevzla.pokedex.ui.theme.typePoison
import com.prodevzla.pokedex.ui.theme.typePsychic
import com.prodevzla.pokedex.ui.theme.typeRock
import com.prodevzla.pokedex.ui.theme.typeSteel
import com.prodevzla.pokedex.ui.theme.typeWater

//used in the filter
fun Filter.getColor(): Color {
    return this.selectedItem.getColor()
}

//used inside the bottom sheet
fun Filterable.getColor(): Color {
    return when (this) {
        is PokemonGeneration -> getColor()
        is PokemonType -> getColor()
//        is Filter.Version -> NeutralGrey
        else -> NeutralGrey
    }
}

fun PokemonGeneration.getColor(): Color {
    return when (this.id) {
        0 -> NeutralGrey
        else -> SelectedGrey
    }
}

fun PokemonType.getColor(): Color {
    return when (this.id) {
        1 -> typeNormal
        2 -> typeFighting
        3 -> typeFlying
        4 -> typePoison
        5 -> typeGround
        6 -> typeRock
        7 -> typeBug
        8 -> typeGhost
        9 -> typeSteel
        10 -> typeFire
        11 -> typeWater
        12 -> typeGrass
        13 -> typeElectric
        14 -> typePsychic
        15 -> typeIce
        16 -> typeDragon
        17 -> typeDark
        18 -> typeFairy
        else -> NeutralGrey
    }
}
