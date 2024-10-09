package com.prodevzla.pokedex.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prodevzla.pokedex.data.source.model.AbilitiesConverter
import com.prodevzla.pokedex.data.source.model.PokemonAbilitiesConverter
import com.prodevzla.pokedex.data.source.model.PokemonEntity
import com.prodevzla.pokedex.data.source.model.PokemonGenerationEntity
import com.prodevzla.pokedex.data.source.model.PokemonInfoEntity
import com.prodevzla.pokedex.data.source.model.PokemonTypeEntity
import com.prodevzla.pokedex.data.source.model.PokemonTypeConverter

@Database(
    entities = [
        PokemonEntity::class,
        PokemonGenerationEntity::class,
        PokemonTypeEntity::class,
        PokemonInfoEntity::class,
    ],
    version = 3
)
@TypeConverters(
    PokemonTypeConverter::class,
    AbilitiesConverter::class,
    PokemonAbilitiesConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonGenerationDao(): PokemonGenerationDao
    abstract fun pokemonTypeDao(): PokemonTypeDao
}
