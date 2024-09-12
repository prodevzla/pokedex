package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetPokemonInfoUseCase
import com.prodevzla.pokedex.presentation.navigation.PokemonDetailRoute
import com.prodevzla.pokedex.presentation.navigation.PokemonNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pokemonInfoUseCase: GetPokemonInfoUseCase
) : ViewModel() {

    val pokemon = savedStateHandle.toRoute<PokemonDetailRoute>(mapOf(typeOf<Pokemon>() to PokemonNavType.PokemonType)).pokemon

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(
        DetailUiState(
            pokemon = pokemon,
            info = CategoryUiState.Loading
        )
    )

    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            pokemonInfoUseCase.invoke(pokemon.id).collect { info ->
                _uiState.update { currentState ->
                    currentState.copy(
                        info = when (info) {
                            Result.Loading -> CategoryUiState.Loading
                            is Result.Error -> CategoryUiState.Error
                            is Result.Success -> CategoryUiState.Content(content = info.data)
                        }
                    )
                }
            }
        }
    }

    fun updateSomething() {
        _uiState.update { currentState ->
            val currentInfo: PokemonInfo = (currentState.info as CategoryUiState.Content<PokemonInfo>).content
            val updatedInfo = currentInfo.copy(weightKg = "5kg")
            currentState.copy(
                info = CategoryUiState.Content(updatedInfo)
            )
        }
    }

}


data class DetailUiState(
    val pokemon: Pokemon,
    val info: CategoryUiState = CategoryUiState.Loading,
    val stats: CategoryUiState = CategoryUiState.Loading,
    val moves: CategoryUiState = CategoryUiState.Loading,
    val additionalInfo: CategoryUiState = CategoryUiState.Loading,
)

sealed interface CategoryUiState {
    data object Loading : CategoryUiState
    data object Error : CategoryUiState
    data class Content<T>(val content: T) : CategoryUiState
}
