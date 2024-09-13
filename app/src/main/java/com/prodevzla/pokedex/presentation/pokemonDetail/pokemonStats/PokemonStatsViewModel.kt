package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonStats

import androidx.lifecycle.SavedStateHandle
import com.prodevzla.pokedex.presentation.pokemonDetail.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonStatsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel(savedStateHandle) {


//    init {
//        println("something")
//        println(pokemon.name)
//    }
}