package com.example.movies.di

import com.example.movies.data.repository.FavoritesRepositoryImpl
import com.example.movies.data.repository.MovieRepositoryImpl
import com.example.movies.domain.repository.FavoritesRepository
import com.example.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(repository: FavoritesRepositoryImpl): FavoritesRepository
}