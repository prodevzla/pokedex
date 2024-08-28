package com.prodevzla.pokedex.domain.model

import java.net.URL

data class Pokemon(
    val id: Int,
    val name: String,
    var image: URL? = null,
    val types: List<Int>,
    val generation: Int?,
    val gameVersions: List<Int>,
)
