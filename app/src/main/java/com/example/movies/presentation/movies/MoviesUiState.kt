package com.example.movies.presentation.movies

import androidx.paging.PagingData
import com.example.movies.domain.model.Movie

data class MoviesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val pagingMovies: PagingData<Movie>? = null,
    val error: String? = null
)