package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "types")
    @androidx.room.TypeConverters(PokemonTypeConverter::class) val types: List<Int>,
    @ColumnInfo(name = "generation") val generation: Int?,
)

class PokemonTypeConverter {
    @androidx.room.TypeConverter
    fun listToInts(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    @androidx.room.TypeConverter
    fun intsToList(string: String): List<Int> {
        return string.split(",").map { it.toInt() }
    }
}
