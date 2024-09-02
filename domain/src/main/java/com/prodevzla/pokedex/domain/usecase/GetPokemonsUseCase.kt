package com.prodevzla.pokedex.domain.usecase

import com.prodevzla.pokedex.domain.model.Pokemon
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import com.prodevzla.pokedex.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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
                                image = IMAGE_URL.replace("{pokemonId}", pokemon.id.toString())
                            }
                        }
                    )
                }
            }.flowOn(Dispatchers.IO)



    companion object {
        const val IMAGE_URL =
//            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/{pokemonId}.svg"
//            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{pokemonId}.png"
//            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/{pokemonId}.png"
//            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/{pokemonId}.png"//gap on the top
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/{pokemonId}.png"
    }

}
