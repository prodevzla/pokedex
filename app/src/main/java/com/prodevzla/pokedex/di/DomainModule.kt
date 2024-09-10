package com.prodevzla.pokedex.di

import com.prodevzla.pokedex.data.repository.AnalyticsRepositoryImpl
import com.prodevzla.pokedex.data.repository.PokemonRepositoryImpl
import com.prodevzla.pokedex.domain.usecase.GetFiltersUseCase
import com.prodevzla.pokedex.domain.usecase.GetGameVersionsUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonInfoUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonGenerationsUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonTypesUseCase
import com.prodevzla.pokedex.domain.usecase.GetPokemonsUseCase
import com.prodevzla.pokedex.domain.usecase.TrackEventUseCase
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
    fun provideGetGameVersionsUseCase(repository: PokemonRepositoryImpl): GetGameVersionsUseCase {
        return GetGameVersionsUseCase(repository)
    }

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

    @Provides
    @Singleton
    fun provideGetFiltersUseCase(
        getPokemonGenerationsUseCase: GetPokemonGenerationsUseCase,
        getPokemonTypesUseCase: GetPokemonTypesUseCase,
    ) = GetFiltersUseCase(getPokemonGenerationsUseCase, getPokemonTypesUseCase)

    @Provides
    @Singleton
    fun provideGetPokemonGenerationsUseCase(repository: PokemonRepositoryImpl): GetPokemonGenerationsUseCase {
        return GetPokemonGenerationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTrackEventUseCase(repository: AnalyticsRepositoryImpl): TrackEventUseCase {
        return TrackEventUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPokemonInfoUseCase(repository: PokemonRepositoryImpl): GetPokemonInfoUseCase {
        return GetPokemonInfoUseCase(repository)
    }

}
