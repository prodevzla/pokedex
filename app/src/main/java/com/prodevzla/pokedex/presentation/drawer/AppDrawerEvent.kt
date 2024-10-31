package com.prodevzla.pokedex.presentation.drawer

import com.prodevzla.pokedex.presentation.navigation.NavigationRoute

sealed class AppDrawerEvent(val route: NavigationRoute? = null) {
    data object ClickFavourites: AppDrawerEvent(NavigationRoute.FavouritesRoute)

    data object ClickAbilities: AppDrawerEvent(NavigationRoute.AbilitiesRoute)

    data object ClickSettings: AppDrawerEvent(NavigationRoute.SettingsRoute)

    data object ClickPalette: AppDrawerEvent()

}
