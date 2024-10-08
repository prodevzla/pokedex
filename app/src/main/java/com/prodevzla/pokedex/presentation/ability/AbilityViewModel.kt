package com.prodevzla.pokedex.presentation.ability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.usecase.GetAbilityUseCase
import com.prodevzla.pokedex.presentation.ability.model.AbilityUiState
import com.prodevzla.pokedex.presentation.util.toStateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel(assistedFactory = AbilityViewModel.MyViewModelFactory::class)
class AbilityViewModel @AssistedInject constructor(
    @Assisted private val abilityId: Int,
    getAbilityUseCase: GetAbilityUseCase,
) : ViewModel() {

    @AssistedFactory
    interface MyViewModelFactory {
        fun create(myParam: Int): AbilityViewModel
    }

    val uiState: StateFlow<AbilityUiState> =
        getAbilityUseCase.invoke(abilityId)
            .map {
                println(abilityId)
                when (it) {
                    Result.Loading -> AbilityUiState.Loading
                    is Result.Error -> AbilityUiState.Error
                    is Result.Success -> AbilityUiState.Content(it.data)
                }
            }
            .toStateFlow(viewModelScope, AbilityUiState.Loading)
}
