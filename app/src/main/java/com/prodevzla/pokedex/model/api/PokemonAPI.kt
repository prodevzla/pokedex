package com.prodevzla.pokedex.model.api

import com.prodevzla.pokedex.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class PokemonAPI(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String, //TODO URL ?
)

fun PokemonAPI.toPokemon(): Pokemon {
    val id = this.url.removeSuffix("/").split("/").last().toInt()

    return Pokemon(
        id = id,
        name = this.name,
        image = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$id.svg")
//        image = URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
    )
}