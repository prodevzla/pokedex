package com.prodevzla.pokedex.presentation.pokemonDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.PokemonAdditionalInfo
import com.prodevzla.pokedex.domain.model.PokemonInfo
import com.prodevzla.pokedex.domain.model.PokemonMoves
import com.prodevzla.pokedex.domain.model.PokemonStats
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

    val pokemon: Pokemon =
        savedStateHandle.toRoute<PokemonDetailRoute>(typeMap = mapOf(typeOf<Pokemon>() to PokemonNavType.PokemonType)).pokemon

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
                        info = if (info is Result.Success) CategoryUiState.Content(content = info.data) else CategoryUiState.Error,
                    )
                }
            }
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

    data class Content<T>(val content: T): CategoryUiState

//    sealed interface Info : CategoryUiState {
//        data class Content(val info: PokemonInfo) : Info
//    }
//
//    sealed interface Stats : CategoryUiState {
//        data class Content(val stats: PokemonStats) : Stats
//    }
//
//    sealed interface Moves : CategoryUiState {
//        data class Content(val moves: PokemonMoves) : Moves
//    }
//
//    sealed interface AdditionalInfo : CategoryUiState {
//        data class Content(val additionalInfo: PokemonAdditionalInfo) : AdditionalInfo
//    }

}

/*
query GetPokemonQuery($pokemonId: Int!) {
    pokemon_v2_pokemon(where: {id: {_eq: $pokemonId}}) {
        id
        name
        height
        weight
        pokemon_v2_pokemontypes {
            pokemon_v2_type {
                id
                name
            }
        }
        pokemon_v2_pokemonspecy {
            id
            gender_rate
            pokemon_v2_pokemonspeciesflavortexts(where: {pokemon_v2_language: {name: {_like: "en"}}}, order_by: {version_id: desc}, limit: 1) {
                version_id
                flavor_text
                pokemon_v2_version {
                    name
                }
            }
        }
        pokemon_v2_pokemoncries {
            id
            cries
        }
    }
}

 */