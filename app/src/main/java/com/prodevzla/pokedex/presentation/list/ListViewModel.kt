package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.GetPokemonsUseCase
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    private var offset = 0
    private val limit = 20

    private val _uiState: MutableStateFlow<ListState> = MutableStateFlow(ListState.Loading)
    val uiState: StateFlow<ListState> = _uiState

    init {
        loadMorePokemon()
    }

    fun loadMorePokemon() {
        if (offset == 0) _uiState.value = ListState.Loading

        getPokemonsUseCase.invoke(limit, offset).onEach { response ->
            _uiState.value = when (response) {
                is Result.Error -> ListState.Error
                is Result.Success -> ListState.Content(
                    data = (uiState.value as? ListState.Content)?.data.orEmpty() + response.data,
                    search = ""
                ).also { offset += limit }
            }
        }.launchIn(viewModelScope)
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
    data object Loading : ListState

    data class Content(val data: List<Pokemon>, val search: String) : ListState

    data object Error : ListState
}
