package com.example.movies.domain.repository

import androidx.paging.PagingData
import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun refreshMovieDetails(movieId: Int)

    fun getMoviesPaging(): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<Details>
}