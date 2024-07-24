package com.prodevzla.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.data.PokemonRepository
import com.prodevzla.pokedex.model.Pokemon
import com.prodevzla.pokedex.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var offset = 0
    private val limit = 20

    private val _uiState = MutableStateFlow(ListUIState())
    val uiState: StateFlow<ListUIState> = _uiState

    init {
        loadMorePokemon()
    }

    fun loadMorePokemon() = viewModelScope.launch {
        if (offset == 0) {
            _uiState.value = _uiState.value.copy(isLoading = true)
        }

        val response = repository.getPokemonList(limit, offset)
        if (response is Result.Error) {
            println("error: ${response.error.name}")
            return@launch
        }

        _uiState.update { state ->
            val newPokemonList = state.pokemonList.toMutableList().apply {
                addAll((response as Result.Success).data)
            }
            state.copy(
                isLoading = false,
                pokemonList = newPokemonList
            )
        }

        offset += limit
    }

    fun onSearchChange(input: String) {
        println("input: $input")
        _uiState.value = _uiState.value.copy(
            search = input,
        )
    }

    data class ListUIState(
        val isLoading: Boolean = true,
        val pokemonList: List<Pokemon> = emptyList(),
        val search: String = "",
    )
}
