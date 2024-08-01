package com.prodevzla.pokedex.model.domain

import java.net.URL

data class Pokemon(val id: Int, val name: String, val image: URL? = null)
