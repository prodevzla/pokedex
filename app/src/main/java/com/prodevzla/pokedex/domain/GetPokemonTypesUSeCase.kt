package com.prodevzla.pokedex.domain

class GetPokemonTypesUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke() = repository.getPokemonTypes()
}