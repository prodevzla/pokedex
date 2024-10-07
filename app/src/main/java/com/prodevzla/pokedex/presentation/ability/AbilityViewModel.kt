package com.prodevzla.pokedex.presentation.ability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prodevzla.pokedex.domain.usecase.GetAbilityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbilityViewModel @Inject constructor(
    getAbilityUseCase: GetAbilityUseCase,
): ViewModel() {

    init {
        viewModelScope.launch {
            getAbilityUseCase.invoke(1).collect {
                println("ability is: $it")
            }
        }
    }
}
