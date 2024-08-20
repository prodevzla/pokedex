package com.prodevzla.pokedex.model.api

import com.prodevzla.pokedex.model.domain.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonAPI(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String, //TODO URL ?
)

//fun PokemonAPI.toPokemon(): Pokemon {
//    val id = this.url.removeSuffix("/").split("/").last().toInt()
//
//    return Pokemon(
//        id = id,
//        name = this.name,
//    )
//}
