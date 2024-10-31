package com.prodevzla.pokedex.presentation.abilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Ability
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetAbilitiesUseCase
import com.prodevzla.pokedex.presentation.abilities.model.AbilitiesUiState
import com.prodevzla.pokedex.presentation.util.RetryableFlowTrigger
import com.prodevzla.pokedex.presentation.util.retryableFlow
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class AbilitiesViewModel @Inject constructor(
    getAbilitiesUseCase: GetAbilitiesUseCase
) : ViewModel() {

    private val retryableFlowTrigger = RetryableFlowTrigger()

    private val _showAbilityDialog: MutableStateFlow<Ability?> = MutableStateFlow(null)

    val uiState = combine(
        retryableFlowTrigger.retryableFlow {
            getAbilitiesUseCase.invoke()
        },
        _showAbilityDialog
    ) { abilities, showDialog ->
        when (abilities) {
            Result.Loading -> AbilitiesUiState.Loading
            is Result.Error -> AbilitiesUiState.Error
            is Result.Success -> AbilitiesUiState.Content(
                abilities = abilities.data,
                showAbilityDialog = showDialog
            )
        }
    }.toStateFlow(viewModelScope, AbilitiesUiState.Loading)

    fun onEvent(event: AbilitiesScreenEvent) {
        when (event) {
            AbilitiesScreenEvent.ClickBack -> {}

            AbilitiesScreenEvent.ClickTryAgain -> retryableFlowTrigger.retry()

            AbilitiesScreenEvent.DismissAbilityDialog -> _showAbilityDialog.value = null

            is AbilitiesScreenEvent.ClickPokemon -> {}

            is AbilitiesScreenEvent.ClickAbility -> _showAbilityDialog.value = event.ability

        }
    }

}
