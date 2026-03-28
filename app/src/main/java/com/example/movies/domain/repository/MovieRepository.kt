package com.example.movies.domain.repository

import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun refreshMovies()

    fun getMovies(): Flow<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Details
}