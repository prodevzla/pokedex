package com.prodevzla.pokedex.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prodevzla.pokedex.domain.model.PokemonAbility

@Entity
data class PokemonInfoEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "genderRate") val genderRate: Int,
    @ColumnInfo(name = "flavorText") val flavorText: String,
    @ColumnInfo(name = "cries") val cries: String,
    @androidx.room.TypeConverters(AbilitiesConverter::class)
    @ColumnInfo(name = "abilities") val abilities: List<PokemonAbility>,
)

class AbilitiesConverter {
    @androidx.room.TypeConverter
    fun fromAbilities(types: List<PokemonAbility>): String {
        return types.joinToString(separator = "###") { "${it.id}@@@${it.name}@@@${it.description}@@@${it.isHidden}" }
    }

    @androidx.room.TypeConverter
    fun toAbilities(typesString: String): List<PokemonAbility> {
        return typesString.split("###").map {
            val sections: List<String> = it.split("@@@")
            PokemonAbility(id = sections[0].toInt(), name = sections[1], description = sections[2], isHidden = sections[3].toBoolean())
        }
    }
}
