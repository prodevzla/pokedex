package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText

@Entity
data class PokemonEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "types")
    @androidx.room.TypeConverters(PokemonTypeConverter::class) val types: List<PokemonType>,
    @ColumnInfo(name = "generation") val generation: Int,
)


class PokemonTypeConverter {
    @androidx.room.TypeConverter
    fun fromPokemonTypes(types: List<PokemonType>): String {
        return types.joinToString(separator = ",") { "${it.id},${it.name.value}" }
    }

    @androidx.room.TypeConverter
    fun toPokemonTypes(typesString: String): List<PokemonType> {
        return typesString.split(",").chunked(2).map {
            PokemonType(id = it[0].toInt(), name = UiText.DynamicString(it[1]))
        }
    }

}
