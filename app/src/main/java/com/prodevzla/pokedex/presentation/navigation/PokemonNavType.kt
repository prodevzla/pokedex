package com.prodevzla.pokedex.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.prodevzla.pokedex.domain.model.Pokemon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PokemonNavType {

    val PokemonType = object : NavType<Pokemon>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Pokemon? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Pokemon {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Pokemon): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Pokemon) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}
