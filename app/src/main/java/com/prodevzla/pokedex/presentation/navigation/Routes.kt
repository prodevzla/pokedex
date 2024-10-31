package com.prodevzla.pokedex.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object HomeRoute: NavigationRoute

//@Serializable
//data class PokemonDetailRoute(
//    val pokemon: Pokemon
//)

    @Serializable
    data class PokemonDetailRoute(
        val id: Int,
    ): NavigationRoute

    @Serializable
    data object FavouritesRoute: NavigationRoute

    @Serializable
    data object AbilitiesRoute: NavigationRoute

    @Serializable
    data object SettingsRoute: NavigationRoute

}