package com.example.movies.data.repository

import com.example.movies.data.local.dao.FavoriteDao
import com.example.movies.data.local.dao.MovieDao
import com.example.movies.data.local.entity.FavoriteEntity
import com.example.movies.data.mapper.toFavorite
import com.example.movies.domain.model.Favorite
import com.example.movies.domain.repository.FavoritesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {

    override fun getFavoriteIds(): Flow<List<Int>> = favoriteDao.getFavoritesIds().map { list ->
        list.map { it.id }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorites(): Flow<List<Favorite>> =
        favoriteDao.getFavoritesIds().flatMapLatest { movieIds ->
            movieDao.getMoviesByIds(movieIds.map { it.id }).map { list ->
                list.map { it.toFavorite() }
            }
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