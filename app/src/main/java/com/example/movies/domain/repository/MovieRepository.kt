package com.example.movies.domain.repository

import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<List<Movie>>
    suspend fun refreshMovies()
}