package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase

import com.prodevzla.pokedex.domain.model.UiText

class GetHeightLabelViewCase {

    operator fun invoke(height: Int): UiText {
        return UiText.DynamicString(value = "${height * 10} cm")
    }

}
