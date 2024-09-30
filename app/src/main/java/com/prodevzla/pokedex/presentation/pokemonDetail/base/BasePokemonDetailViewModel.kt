package com.prodevzla.pokedex.presentation.pokemonDetail.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.prodevzla.pokedex.presentation.navigation.PokemonDetailRoute

fun SavedStateHandle.getPokemonId(): Int {
//    return this.toRoute<PokemonDetailRoute>(mapOf(typeOf<Pokemon>() to PokemonNavType.PokemonType)).pokemon
    return this.toRoute<PokemonDetailRoute>().id
}

/**
 * Used by the PokemonInfoScreen. It extends the AndroidViewModel as this screen need to reproduce some audio through the context class
 */
//abstract class BaseAppViewModel(
//    application: Application,
//    private val savedStateHandle: SavedStateHandle
//) : AndroidViewModel(application), PokemonInterface {
//
//    override val pokemon: Pokemon by lazy {
//        savedStateHandle.getPokemon()
//    }
//}

/**
 * Used by the PokemonStats, PokemonMoves and PokemonMore screens
 */
abstract class BasePokemonDetailViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), PokemonInterface {

    override val pokemonId: Int
        get() = savedStateHandle.getPokemonId()

}

interface PokemonInterface {

    val pokemonId: Int

}
