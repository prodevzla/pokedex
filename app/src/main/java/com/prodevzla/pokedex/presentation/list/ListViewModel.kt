package com.prodevzla.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.GetPokemonsUseCase
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    val uiState: StateFlow<ListState> = getPokemonsUseCase.invoke()
        .map { data ->
            when (data) {
                is Result.Error -> ListState.Error
                is Result.Success -> ListState.Content(data.data)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListState.Loading
        )

}

sealed interface ListState {
    data object Loading : ListState

    data class Content(val data: List<Pokemon>, val search: String = "") : ListState

    data object Error : ListState
}
