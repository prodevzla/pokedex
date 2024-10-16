@file:OptIn(ExperimentalMaterial3Api::class)

package com.prodevzla.pokedex.presentation.ability

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetAbilityUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonsByAbilityUseCase
import com.prodevzla.pokedex.domain.usecase.ToggleSavePokemonUseCase
import com.prodevzla.pokedex.presentation.ability.model.AbilityUiState
import com.prodevzla.pokedex.presentation.util.RetryableFlowTrigger
import com.prodevzla.pokedex.presentation.util.retryableFlow
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AbilityViewModel.MyViewModelFactory::class)
class AbilityViewModel @AssistedInject constructor(
    @Assisted private val abilityId: Int,
    getAbilityUseCase: GetAbilityUseCase,
    getPokemonsByAbilityUseCase: GetPokemonsByAbilityUseCase,
    private val toggleSavePokemonUseCase: ToggleSavePokemonUseCase,
) : ViewModel() {

    @AssistedFactory
    interface MyViewModelFactory {
        fun create(myParam: Int): AbilityViewModel
    }

    private val _sheetState = MutableStateFlow(SheetValue.Hidden)

    private val retryableFlowTrigger = RetryableFlowTrigger()

    val uiState: StateFlow<AbilityUiState> = combine(
        retryableFlowTrigger.retryableFlow {
            getAbilityUseCase.invoke(abilityId)
        },
        getPokemonsByAbilityUseCase.invoke(abilityId),
        _sheetState,
    ) { ability, pokemons, sheetState ->

        when {
            ability is Result.Loading || pokemons is Result.Loading ->
                AbilityUiState.Loading

            ability is Result.Success && pokemons is Result.Success ->
                AbilityUiState.Content(ability.data, pokemons.data, sheetState)

            else -> AbilityUiState.Error
        }

    }.toStateFlow(viewModelScope, AbilityUiState.Loading)

    fun onEvent(event: AbilityScreenEvent) {
        when (event) {
            is AbilityScreenEvent.OnClickPokemon -> println("1")
            is AbilityScreenEvent.ToggleSave -> {
                viewModelScope.launch(Dispatchers.IO) {
                    toggleSavePokemonUseCase.invoke(event.pokemon.id)
                }
            }

            is AbilityScreenEvent.SheetStateChange ->
                _sheetState.value = event.sheetState

            is AbilityScreenEvent.ClickTryAgain ->
                retryableFlowTrigger.retry()
        }
    }
}
