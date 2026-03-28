package com.example.movies.domain.repository

import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun refreshMovies()
    suspend fun refreshMovieDetails(movieId: Int)

    fun getMovies(): Flow<List<Movie>>
    fun getMovieDetails(movieId: Int): Flow<Details>
}