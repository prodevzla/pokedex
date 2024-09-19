package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.prodevzla.pokedex.data.source.model.PokemonEntity
import com.prodevzla.pokedex.data.source.model.PokemonInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao: BaseDao<PokemonEntity> {

    @Query("SELECT * FROM pokemonentity")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemonInfoEntity WHERE uid = :id")
    fun getPokemonInfo(id: Int): PokemonInfoEntity?

    @Insert
    fun insertPokemonInfo(pokemonInfoEntity: PokemonInfoEntity)

    @Query("SELECT * FROM pokemonentity WHERE uid = :id")
    fun getPokemon(id: Int): Flow<PokemonEntity>

    @Update
    fun updateSaveStatus(pokemonEntity: PokemonEntity)

}
