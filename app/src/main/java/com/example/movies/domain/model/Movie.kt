package com.example.movies.domain.model

data class Movie(
    val id: Int? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val isFavorite: Boolean = false
)