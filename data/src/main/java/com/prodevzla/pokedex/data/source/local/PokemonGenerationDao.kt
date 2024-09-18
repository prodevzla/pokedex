package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonGenerationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonGenerationDao: BaseDao<PokemonGenerationEntity> {

    @Query("SELECT * FROM pokemongenerationentity")
    fun getAll(): Flow<List<PokemonGenerationEntity>>

}
