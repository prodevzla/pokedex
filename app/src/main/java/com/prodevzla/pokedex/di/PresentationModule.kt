package com.prodevzla.pokedex.di

import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase.GetHeightLabelViewCase
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase.GetWeightLabelViewCase
import com.prodevzla.pokedex.presentation.pokemonDetail.pokemonInfo.viewcase.TransformPokemonInfoIntoModelViewCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PresentationModule {

    @Provides
    @Singleton
    fun provideGetHeightLabelViewCase(): GetHeightLabelViewCase {
        return GetHeightLabelViewCase()
    }

    @Provides
    @Singleton
    fun provideGetWeightLabelViewCase(): GetWeightLabelViewCase {
        return GetWeightLabelViewCase()
    }

    @Provides
    @Singleton
    fun provideTransformPokemonInfoIntoModelViewCase(
        getHeightLabelViewCase: GetHeightLabelViewCase,
        getWeightLabelViewCase: GetWeightLabelViewCase
    ): TransformPokemonInfoIntoModelViewCase {
        return TransformPokemonInfoIntoModelViewCase(getHeightLabelViewCase, getWeightLabelViewCase)
    }

}
