package com.example.movies.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.data.local.dao.FavoriteDao
import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "movies.db")
            .fallbackToDestructiveMigrationFrom(true)
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.moviesDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDao = appDatabase.favoriteDao()
}