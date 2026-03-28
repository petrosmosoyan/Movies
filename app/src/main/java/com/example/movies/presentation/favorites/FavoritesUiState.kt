package com.example.movies.presentation.favorites

import com.example.movies.domain.model.Favorite

data class FavoritesUiState(
    val favorites: List<Favorite>? = null,
)