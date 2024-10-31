package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.model.Result
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetFavouritesUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(): Flow<Result<List<Pokemon>>> =
        repository.getFavourites()
            .asResult()

}
