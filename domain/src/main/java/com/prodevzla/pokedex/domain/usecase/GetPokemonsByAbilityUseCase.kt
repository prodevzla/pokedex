package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.map

class GetPokemonsByAbilityUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(ability: Int) = repository.getPokemonList()
        .map { list ->
            list.filter { pokemon ->
                pokemon.abilities.contains(ability)
            }
        }.asResult()

}
