package com.example.movies.domain.repository

import com.example.movies.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteIds(): Flow<List<Int>>
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun toggleFavorite(movieId: Int)
}