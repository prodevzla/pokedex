package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonGenerationEntity

@Dao
interface PokemonGenerationDao {
    @Query("SELECT * FROM pokemongenerationentity")
    fun getAll(): List<PokemonGenerationEntity>

    @Insert
    fun insertAll(vararg pokemons: PokemonGenerationEntity)

}
