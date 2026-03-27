package com.example.movies.domain.repository

import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun refreshMovies()

    fun getMovies(): Flow<List<Movie>>
    fun getFavorites(): Flow<List<Int>>
    suspend fun toggleFavorite(movieId: Int)
}