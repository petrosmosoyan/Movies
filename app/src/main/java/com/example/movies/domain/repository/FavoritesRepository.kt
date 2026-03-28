package com.example.movies.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavorites(): Flow<List<Int>>
    suspend fun toggleFavorite(movieId: Int)
}