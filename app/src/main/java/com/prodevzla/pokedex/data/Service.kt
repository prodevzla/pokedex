package com.prodevzla.pokedex.data

import com.prodevzla.pokedex.model.api.APIResponse
import com.prodevzla.pokedex.model.api.PokemonAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<APIResponse<List<PokemonAPI>>>

}
