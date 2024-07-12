package com.prodevzla.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.data.PokemonRepository
import com.prodevzla.pokedex.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUIState())
    val uiState: StateFlow<ListUIState> = _uiState

    init {

        viewModelScope.launch {
            val response = repository.getPokemonList()

            _uiState.value = _uiState.value.copy(
                pokemonList = response.getOrNull() ?: emptyList()
            )

        }
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
