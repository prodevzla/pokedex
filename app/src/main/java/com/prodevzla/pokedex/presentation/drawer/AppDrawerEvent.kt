package com.prodevzla.pokedex.presentation.drawer

sealed interface AppDrawerEvent {
    data object ClickAbilities: AppDrawerEvent
    data object ClickSettings: AppDrawerEvent
}