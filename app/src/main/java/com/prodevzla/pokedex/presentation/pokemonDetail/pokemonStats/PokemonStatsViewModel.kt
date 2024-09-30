package com.prodevzla.pokedex.presentation.pokemonDetail.pokemonStats

import androidx.lifecycle.SavedStateHandle
import com.prodevzla.pokedex.presentation.pokemonDetail.base.BasePokemonDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonStatsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BasePokemonDetailViewModel(savedStateHandle) {


//    init {
//        println("something")
//        println(pokemon.name)
//    }
}