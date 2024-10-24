package com.prodevzla.pokedex.data.mapper

interface Mapper<A,D,E> {
    fun mapToDomain(api: List<A>): List<D>
    fun mapToDomain(api: A): D
    fun mapToEntity(domain: D): E
}
