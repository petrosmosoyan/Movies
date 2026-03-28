package com.example.movies.data.repository

import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.mapper.toDomain
import com.example.movies.data.mapper.toEntity
import com.example.movies.data.remote.api.MovieApi
import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
) : MovieRepository {

    override suspend fun refreshMovies() {
        val response = movieApi.getMovies()
        val movies = response.movieDtos ?: return
        movieDao.insertAll(movies.map { it.toEntity() })
    }

    override fun getMovies(): Flow<List<Movie>> = movieDao.getMovies().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getMovieDetails(movieId: Int): Details {
        val response = movieApi.getDetails(movieId = movieId)
        return response.toDomain()
    }
}