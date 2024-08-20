package com.prodevzla.pokedex.di

import com.prodevzla.pokedex.data.PokemonRepositoryImpl
import com.prodevzla.pokedex.domain.GetPokemonTypesUseCase
import com.prodevzla.pokedex.domain.GetPokemonsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    @Singleton
    fun provideGetPokemonUseCase(repository: PokemonRepositoryImpl): GetPokemonsUseCase {
        return GetPokemonsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPokemonTypesUseCase(repository: PokemonRepositoryImpl): GetPokemonTypesUseCase {
        return GetPokemonTypesUseCase(repository)
    }

}
