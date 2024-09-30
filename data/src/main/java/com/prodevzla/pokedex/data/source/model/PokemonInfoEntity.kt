package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prodevzla.pokedex.domain.model.Ability

@Entity
data class PokemonInfoEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "genderRate") val genderRate: Int,
    @ColumnInfo(name = "flavorText") val flavorText: String,
    @ColumnInfo(name = "cries") val cries: String,
    @androidx.room.TypeConverters(AbilitiesConverter::class)
    @ColumnInfo(name = "abilities") val abilities: List<Ability>,
)

class AbilitiesConverter {
    @androidx.room.TypeConverter
    fun fromAbilities(types: List<Ability>): String {
        return types.joinToString(separator = ",") { "${it.name},${it.description},${it.isHidden}" }
    }

    @androidx.room.TypeConverter
    fun toAbilities(typesString: String): List<Ability> {
        return typesString.split(",").chunked(3).map {
            Ability(name = it[0], description = it[1], isHidden = it[2].toBoolean())
        }
    }

}
