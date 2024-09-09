package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.usecase.GetPokemonDetailsUseCase
import com.prodevzla.pokedex.presentation.navigation.PokemonDetailRoute
import com.prodevzla.pokedex.presentation.navigation.PokemonNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {

    init {

        val pokemon =
            savedStateHandle.toRoute<PokemonDetailRoute>(typeMap = mapOf(typeOf<Pokemon>() to PokemonNavType.PokemonType)).pokemon


        viewModelScope.launch {
            pokemonDetailsUseCase.invoke(pokemon.id).collect {
                println(it)

            }
        }
    }
}
