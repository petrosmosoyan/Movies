package com.example.movies.presentation.details

import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Movie

data class DetailsUiState(
    val isLoading: Boolean = false,
    val details: Details? = null,
    val error: String? = null
)