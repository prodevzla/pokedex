package com.prodevzla.pokedex.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetFavouritesUseCase
import com.prodevzla.pokedex.domain.usecase.ToggleSavePokemonUseCase
import com.prodevzla.pokedex.presentation.favourites.model.FavouritesState
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    getFavouritesUseCase: GetFavouritesUseCase,
    private val toggleSavePokemonUseCase: ToggleSavePokemonUseCase,
) : ViewModel() {

    val uiState = getFavouritesUseCase.invoke()
        .map {
            when (it) {
                Result.Loading -> FavouritesState.Loading
                is Result.Error -> FavouritesState.Error
                is Result.Success -> FavouritesState.Content(it.data)
            }
        }
        .toStateFlow(viewModelScope, FavouritesState.Loading)

    fun onEvent(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.ToggleSave -> {
                viewModelScope.launch(Dispatchers.IO) {
                    toggleSavePokemonUseCase.invoke(event.pokemon.id)
                }
            }
            else -> Unit
        }
    }

}
