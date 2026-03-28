package com.example.movies.domain.model

data class Favorite(
    val id: Int? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null
)
