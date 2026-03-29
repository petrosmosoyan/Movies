package com.example.movies.domain.repository

interface FavoritesRepository {
    suspend fun toggleFavorite(movieId: Int)
}