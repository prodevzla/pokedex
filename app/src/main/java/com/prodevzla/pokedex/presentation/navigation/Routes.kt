package com.prodevzla.pokedex.presentation.navigation

import com.prodevzla.pokedex.domain.model.Pokemon
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
object Favourites
