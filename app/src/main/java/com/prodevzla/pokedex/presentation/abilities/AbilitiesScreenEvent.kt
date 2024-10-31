package com.prodevzla.pokedex.presentation.abilities

import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.Pokemon

sealed interface AbilitiesScreenEvent {

    data object ClickBack: AbilitiesScreenEvent

    data class ClickAbility(val ability: Ability): AbilitiesScreenEvent

    data object DismissAbilityDialog: AbilitiesScreenEvent

    data object ClickTryAgain: AbilitiesScreenEvent

    data class ClickPokemon(val pokemon: Pokemon): AbilitiesScreenEvent

}
