package com.prodevzla.pokedex.presentation.ability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetAbilityUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonsByAbilityUseCase
import com.prodevzla.pokedex.domain.usecase.ToggleSavePokemonUseCase
import com.prodevzla.pokedex.presentation.ability.model.AbilityUiState
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    val uiState: StateFlow<AbilityUiState> = combine(
        getAbilityUseCase.invoke(abilityId),
        getPokemonsByAbilityUseCase.invoke(abilityId)
    ) { ability, pokemons ->

        when {
            ability is Result.Loading || pokemons is Result.Loading ->
                AbilityUiState.Loading

            ability is Result.Success && pokemons is Result.Success ->
                AbilityUiState.Content(ability.data, pokemons.data)

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
        }
    }
}
