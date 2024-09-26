package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prodevzla.pokedex.domain.model.PokemonType
import com.prodevzla.pokedex.domain.model.UiText

@Entity
data class PokemonInfoEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "genderRate") val genderRate: Int,
    @ColumnInfo(name = "flavorText") val flavorText: String,
    @ColumnInfo(name = "cries") val cries: String,
    @androidx.room.TypeConverters(AbilitiesConverter::class)
    @ColumnInfo(name = "abilities") val abilities: List<String>,

)

class AbilitiesConverter {
    @androidx.room.TypeConverter
    fun fromAbility(types: List<String>): String {
        return types.joinToString(separator = ",")
    }

    @androidx.room.TypeConverter
    fun toAbility(typesString: String): List<String> {
        return typesString.split(",")
    }

}
