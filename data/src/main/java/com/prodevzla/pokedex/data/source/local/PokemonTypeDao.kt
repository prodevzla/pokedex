package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonTypeEntity

@Dao
interface PokemonTypeDao {
    @Query("SELECT * FROM pokemontypeentity")
    fun getAll(): List<PokemonTypeEntity>

    @Insert
    fun insertAll(vararg types: PokemonTypeEntity)

}
