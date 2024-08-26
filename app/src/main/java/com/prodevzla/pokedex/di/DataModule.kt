package com.prodevzla.pokedex.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.prodevzla.pokedex.data.repository.PokemonRepositoryImpl
import com.prodevzla.pokedex.data.source.local.AppDatabase
import com.prodevzla.pokedex.data.source.local.PokemonDao
import com.prodevzla.pokedex.data.source.local.PokemonGenerationDao
import com.prodevzla.pokedex.data.source.local.PokemonTypeDao
import com.prodevzla.pokedex.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "pokemon-db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDatabase: AppDatabase): PokemonDao {
        return appDatabase.pokemonDao()
    }

    @Provides
    @Singleton
    fun providePokemonGenerationDao(appDatabase: AppDatabase): PokemonGenerationDao {
        return appDatabase.pokemonGenerationDao()
    }

    @Provides
    @Singleton
    fun providePokemonTypeDao(appDatabase: AppDatabase): PokemonTypeDao {
        return appDatabase.pokemonTypeDao()
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        apolloClient: ApolloClient,
        pokemonDao: PokemonDao,
        pokemonGenerationDao: PokemonGenerationDao,
        pokemonTypeDao: PokemonTypeDao,
    ): PokemonRepository {
        return PokemonRepositoryImpl(
            apolloClient,
            pokemonDao,
            pokemonGenerationDao,
            pokemonTypeDao)
    }

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()
    }

}
