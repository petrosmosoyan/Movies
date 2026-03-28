package com.example.movies.domain.model

data class Movie(
    val id: Int,
    val title: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val isFavorite: Boolean = false
)