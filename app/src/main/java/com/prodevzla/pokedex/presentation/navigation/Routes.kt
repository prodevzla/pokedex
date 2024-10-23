package com.prodevzla.pokedex.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

//@Serializable
//data class PokemonDetailRoute(
//    val pokemon: Pokemon
//)

@Serializable
data class PokemonDetailRoute(
    val id: Int,
)

@Serializable
object FavouritesRoute

@Serializable
object AbilitiesRoute

@Serializable
object SettingsRoute