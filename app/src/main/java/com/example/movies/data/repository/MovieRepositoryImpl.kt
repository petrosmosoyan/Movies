package com.example.movies.data.repository

import com.example.movies.data.local.dao.FavoriteDao
import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.entity.FavoriteEntity
import com.example.movies.data.mapper.toDomain
import com.example.movies.data.mapper.toEntity
import com.example.movies.data.remote.api.MovieApi
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val favoriteDao: FavoriteDao
) : MovieRepository {

    override suspend fun refreshMovies() {
        val response = movieApi.getMovies()
        val movies = response.movieDtos ?: return
        movieDao.insertAll(movies.map { it.toEntity() })
    }

    override fun getMovies(): Flow<List<Movie>> = movieDao.getMovies().map { list ->
        list.map { it.toDomain() }
    }

    override fun getFavorites(): Flow<List<Int>> = favoriteDao.getFavorites().map { list ->
        list.map { it.id }
    }

    override suspend fun toggleFavorite(movieId: Int) {
        val isFavorite = favoriteDao.isFavorite(movieId)

        val favoriteEntity = FavoriteEntity(movieId)
        if (isFavorite)
            favoriteDao.removeFavorite(favoriteEntity)
        else
            favoriteDao.addFavorite(favoriteEntity)
    }
}