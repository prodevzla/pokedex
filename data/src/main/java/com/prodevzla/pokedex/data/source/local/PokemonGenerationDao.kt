package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonGenerationEntity

@Dao
interface PokemonGenerationDao: BaseDao<PokemonGenerationEntity> {

    @Query("SELECT * FROM pokemongenerationentity")
    fun getAll(): List<PokemonGenerationEntity>

}
