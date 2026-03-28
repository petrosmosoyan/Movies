package com.example.movies.data.repository

import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.mapper.toDetails
import com.example.movies.data.mapper.toMovie
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

    override suspend fun refreshMovieDetails(movieId: Int) {
        val response = movieApi.getDetails(movieId = movieId)
        movieDao.updateMovie(response.toEntity())
    }

    override fun getMovies(): Flow<List<Movie>> = movieDao.getMovies().map { list ->
        list.map { it.toMovie() }
    }

    override fun getMovieDetails(movieId: Int): Flow<Details> =
        movieDao.getMovieDetails(movieId).map { it.toDetails() }
}