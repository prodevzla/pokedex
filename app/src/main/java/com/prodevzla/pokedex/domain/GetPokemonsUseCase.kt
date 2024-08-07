package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.data.PokemonRepository
import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.URL

class GetPokemonsUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(limit: Int, offset: Int): Flow<Result<List<Pokemon>>> = flow {
        when (val response = repository.getPokemonList(limit, offset)) {
            is Result.Error -> emit(response)
            is Result.Success -> {
                val list = response.data.map {
                    Pokemon(
                        id = it.id,
                        name = it.name,
                        image = URL(IMAGE_URL + it.id + ".svg")

                    )
                }.toList()
                emit(Result.Success(list))
            }
        }


    }

    companion object {
        const val IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/"
    }

}
