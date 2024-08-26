package com.prodevzla.pokedex.data.source.local

import androidx.room.Insert

interface BaseDao<T> {

    @Insert
    fun insertAll(vararg data: T)

}