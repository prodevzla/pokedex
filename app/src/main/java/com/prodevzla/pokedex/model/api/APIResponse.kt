package com.prodevzla.pokedex.model.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class APIResponse<T>(
    @Json(name = "count") val count: Int,
    @Json(name = "next") val next: String,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: T
)
