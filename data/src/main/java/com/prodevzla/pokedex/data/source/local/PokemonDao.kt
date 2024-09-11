package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonEntity
import com.prodevzla.pokedex.data.source.model.PokemonInfoEntity

@Dao
interface PokemonDao: BaseDao<PokemonEntity> {

    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemonInfoEntity WHERE uid = :id")
    fun getPokemonInfo(id: Int): PokemonInfoEntity?

    @Insert
    fun insertPokemonInfo(pokemonInfoEntity: PokemonInfoEntity)

}
