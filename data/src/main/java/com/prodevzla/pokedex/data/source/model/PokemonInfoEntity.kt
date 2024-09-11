package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonInfoEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "genderRate") val genderRate: Int,
    @ColumnInfo(name = "flavorText") val flavorText: String,
    @ColumnInfo(name = "cries") val cries: String,
)
