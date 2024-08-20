package com.prodevzla.pokedex.domain

import com.prodevzla.pokedex.model.domain.Pokemon
import com.prodevzla.pokedex.model.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.URL

class GetPokemonsUseCase(
    private val repository: PokemonRepository
) {

    operator fun invoke(): Flow<Result<List<Pokemon>>> =
        repository.getPokemonList()
            .map { response ->
                when (response) {
                    is Result.Error -> response
                    is Result.Success -> Result.Success(
                        response.data.map { pokemon ->
                            pokemon.apply {
                                image = URL(IMAGE_URL + pokemon.id + ".svg")
                            }
                        }
                    )
                }
            }



    companion object {
        const val IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/"
    }

}
