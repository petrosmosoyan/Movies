package com.example.movies.domain.model

data class Movie(
    val id: Int?, val title: String?,
    val poster: String?,
    val isFavorite: Boolean = false
)