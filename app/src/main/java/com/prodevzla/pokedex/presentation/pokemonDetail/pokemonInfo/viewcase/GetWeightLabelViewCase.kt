package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase

import com.prodevzla.pokedex.domain.model.UiText

class GetWeightLabelViewCase {

    operator fun invoke(weight: Int): UiText {
        return UiText.DynamicString(value = "${weight / 10} Kg")
    }

}
