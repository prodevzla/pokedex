package com.prodevzla.pokedex.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.prodevzla.pokedex.data.source.model.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

    @Insert
    fun insertAll(vararg pokemons: PokemonEntity)

//    @Delete
//    fun delete(user: User)
}