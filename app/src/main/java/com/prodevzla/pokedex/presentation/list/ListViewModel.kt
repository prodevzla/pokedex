package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.data.PokemonRepository
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
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

    private val _uiState: MutableStateFlow<ListState> = MutableStateFlow(ListState.Loading)
    val uiState: StateFlow<ListState> = _uiState

    init {
        loadMorePokemon()
    }

    fun loadMorePokemon() = viewModelScope.launch {
        if (offset == 0) {
            _uiState.value = ListState.Loading
        }

        val response = repository.getPokemonList(limit, offset)
        if (response is Result.Error) {
            println("error: ${response.error.name}")
            _uiState.value = ListState.Error
            return@launch
        }
        (response as Result.Success)

        if (_uiState.value is ListState.Loading || _uiState.value is ListState.Error) {
            _uiState.value = ListState.Content(
                data = response.data,
                search = ""
            )
        }

        if (_uiState.value is ListState.Content) {
            _uiState.update { state ->
                val currentContent = state as ListState.Content
                val newList = currentContent.data.toMutableList().apply {
                    addAll(response.data)
                }

                currentContent.copy(
                    data = newList
                )
            }
        }



        offset += limit
    }

    fun onSearchChange(input: String) {
        println("input: $input")
        _uiState.update { state ->
            if (state is ListState.Content) {
                state.copy(search = input)
            } else {
                state
            }
        }
    }


}

sealed interface ListState {
    data object Loading: ListState

    data class Content(val data: List<Pokemon>, val search: String): ListState

    data object Error: ListState
}
