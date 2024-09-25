package com.prodevzla.pokedex.presentation.pokemonDetail.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.presentation.navigation.PokemonDetailRoute
import com.prodevzla.pokedex.presentation.navigation.PokemonNavType
import kotlin.reflect.typeOf

fun SavedStateHandle.getPokemon(): Pokemon {
    return this.toRoute<PokemonDetailRoute>(mapOf(typeOf<Pokemon>() to PokemonNavType.PokemonType)).pokemon
}

/**
 * Used by the PokemonInfoScreen. It extends the AndroidViewModel as this screen need to reproduce some audio through the context class
 */
abstract class BaseAppViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application), PokemonInterface {

    override val pokemon: Pokemon by lazy {
        savedStateHandle.getPokemon()
    }
}

/**
 * Used by the PokemonStats, PokemonMoves and PokemonMore screens
 */
abstract class BaseViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), PokemonInterface {

    override val pokemon: Pokemon
        get() = savedStateHandle.getPokemon()

}

interface PokemonInterface {

    val pokemon: Pokemon

}
