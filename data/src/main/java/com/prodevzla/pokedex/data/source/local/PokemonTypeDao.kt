package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonTypeDao: BaseDao<PokemonTypeEntity> {

    @Query("SELECT * FROM pokemontypeentity")
    fun getAll(): Flow<List<PokemonTypeEntity>>

}
