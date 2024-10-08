package com.prodevzla.pokedex.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.LoggingInterceptor
import com.prodevzla.pokedex.data.repository.AbilityRepositoryImpl
import com.prodevzla.pokedex.data.repository.AnalyticsRepositoryImpl
import com.prodevzla.pokedex.data.repository.AudioRepositoryImpl
import com.prodevzla.pokedex.data.repository.PokemonRepositoryImpl
import com.prodevzla.pokedex.data.source.local.AppDatabase
import com.prodevzla.pokedex.data.source.local.PokemonDao
import com.prodevzla.pokedex.data.source.local.PokemonGenerationDao
import com.prodevzla.pokedex.data.source.local.PokemonTypeDao
import com.prodevzla.pokedex.data.source.local.audioPlayer.MediaPlayer
import com.prodevzla.pokedex.data.source.local.audioPlayer.TTSPlayer
import com.prodevzla.pokedex.data.source.remote.AnalyticsService
import com.prodevzla.pokedex.domain.repository.AbilityRepository
import com.prodevzla.pokedex.domain.repository.AnalyticsRepository
import com.prodevzla.pokedex.domain.repository.AudioRepository
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
        ).fallbackToDestructiveMigration().build()
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
            .addHttpInterceptor(LoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideAnalyticsTracker(): AnalyticsService {
        return AnalyticsService()
    }

    @Provides
    @Singleton
    fun provideAnalyticsTrackerRepository(analyticsService: AnalyticsService): AnalyticsRepository {
        return AnalyticsRepositoryImpl(analyticsService)
    }

    //TODO EVALUATE IF MEDIA PLAYERS SHOULD BE SINGLETONS. SAME FOR REPO
    @Provides
    @Singleton
    fun provideTTSPlayer(@ApplicationContext appContext: Context): TTSPlayer {
        return TTSPlayer(appContext)
    }

    @Provides
    @Singleton
    fun provideMediaPlayer(@ApplicationContext appContext: Context): MediaPlayer {
        return MediaPlayer(appContext)
    }

    @Provides
    @Singleton
    fun provideAudioRepository(ttsPlayer: TTSPlayer, mediaPlayer: MediaPlayer): AudioRepository {
        return AudioRepositoryImpl(ttsPlayer, mediaPlayer)
    }

    @Provides
    @Singleton
    fun provideAbilityRepository(apolloClient: ApolloClient): AbilityRepository {
        return AbilityRepositoryImpl(apolloClient)
    }

}
