package com.example.movies.presentation.movies

import com.example.movies.domain.model.Movie

data class MoviesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null
)