package com.example.movies.data.repository

import com.example.movies.data.local.dao.FavoriteDao
import com.example.movies.data.local.entity.FavoriteEntity
import com.example.movies.domain.repository.FavoritesRepository
import jakarta.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {

    override suspend fun toggleFavorite(movieId: Int) {
        val isFavorite = favoriteDao.isFavorite(movieId)

        val favoriteEntity = FavoriteEntity(movieId)
        if (isFavorite)
            favoriteDao.removeFavorite(favoriteEntity)
        else
            favoriteDao.addFavorite(favoriteEntity)
    }
}