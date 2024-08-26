package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonEntity

@Dao
interface PokemonDao: BaseDao<PokemonEntity> {

    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

}
