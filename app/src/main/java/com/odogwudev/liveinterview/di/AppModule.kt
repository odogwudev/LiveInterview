package com.odogwudev.liveinterview.di

import android.content.Context
import androidx.room.Room
import com.odogwudev.liveinterview.data.domain.rrom.AppDB
import com.odogwudev.liveinterview.data.domain.rrom.FavoriteDao
import com.odogwudev.liveinterview.data.network.RickyApi
import com.odogwudev.liveinterview.repository.CharacterRepository
import com.odogwudev.liveinterview.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideApi(json: Json, client : OkHttpClient): RickyApi = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build().create(RickyApi::class.java)

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDB =
        Room.databaseBuilder(context, AppDB::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideFavoritesDao(db: AppDB): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun provideOkHTTpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(impl: CharacterRepositoryImpl): CharacterRepository
}