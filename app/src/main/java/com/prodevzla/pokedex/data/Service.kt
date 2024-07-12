package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.api.APIResponse
import com.prodevzla.pokedex.model.api.PokemonAPI
import retrofit2.Response
import retrofit2.http.GET

interface Service {

    @GET("pokemon")
    suspend fun getPokemonList(): Response<APIResponse<List<PokemonAPI>>>

}
