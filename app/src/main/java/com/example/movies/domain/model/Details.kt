package com.example.movies.domain.model

data class Details(
    val title: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val budget: Int? = null,
    val revenue: Int? = null,
    val homePage: String? = null,
)