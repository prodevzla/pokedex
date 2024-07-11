package com.prodevzla.pokedex.viewModel

import androidx.lifecycle.ViewModel
import com.prodevzla.pokedex.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListUIState())
    val uiState: StateFlow<ListUIState> = _uiState

    init {
        val list = listOf(
            Pokemon(
                "Bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            ),
            Pokemon("Charmander", ""),
            Pokemon("Squirtle", "https://pokeapi.co/api/v2/pokemon/7/")
        )
        _uiState.value = ListUIState(list)
    }

    fun onSearchChange(input: String) {
        println("input: $input")
        _uiState.value = _uiState.value.copy(
            search = input,
        )
    }

    data class ListUIState(
        val pokemonList: List<Pokemon> = emptyList(),
        val search: String = "",
    )
}

