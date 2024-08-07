package com.prodevzla.pokedex.di

import com.prodevzla.pokedex.data.PokemonRepositoryImpl
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
    fun provideGetPokemonUseCase(pokemonRepository: PokemonRepositoryImpl): GetPokemonsUseCase {
        return GetPokemonsUseCase(pokemonRepository)
    }

}
