package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonGenerationEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
)
