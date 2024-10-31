package com.prodevzla.pokedex.presentation.abilities

import com.prodevzla.pokedex.domain.model.Ability

sealed interface AbilitiesScreenEvent {

    data object OnClickBack: AbilitiesScreenEvent

    data class OnClickAbility(val ability: Ability): AbilitiesScreenEvent

    data object ClickTryAgain: AbilitiesScreenEvent

}
