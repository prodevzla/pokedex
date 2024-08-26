package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "types") val types: Int,
    @ColumnInfo(name = "generation") val generation: Int?,
)

