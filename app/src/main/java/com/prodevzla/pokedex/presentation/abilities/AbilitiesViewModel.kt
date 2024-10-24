package com.prodevzla.pokedex.presentation.abilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetAbilitiesUseCase
import com.prodevzla.pokedex.presentation.abilities.model.AbilitiesUiState
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AbilitiesViewModel @Inject constructor(
    getAbilitiesUseCase: GetAbilitiesUseCase
): ViewModel() {

    val uiState = getAbilitiesUseCase.invoke().map {
        when (it) {
            Result.Loading -> AbilitiesUiState.Loading
            is Result.Error -> AbilitiesUiState.Error
            is Result.Success -> AbilitiesUiState.Content(
                abilities = it.data
            )
        }
    }.toStateFlow(viewModelScope, AbilitiesUiState.Loading)

    fun onEvent(event: AbilitiesScreenEvent) {
        when (event) {
            AbilitiesScreenEvent.OnClickBack -> {}
            is AbilitiesScreenEvent.OnClickAbility -> {}
        }
    }

}
