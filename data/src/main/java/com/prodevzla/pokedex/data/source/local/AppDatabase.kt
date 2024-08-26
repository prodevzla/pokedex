package com.prodevzla.pokedex.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prodevzla.pokedex.data.source.model.PokemonEntity
import com.prodevzla.pokedex.data.source.model.PokemonGenerationEntity
import com.prodevzla.pokedex.data.source.model.PokemonTypeEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonGenerationEntity::class,
        PokemonTypeEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonGenerationDao(): PokemonGenerationDao
    abstract fun pokemonTypeDao(): PokemonTypeDao
}
