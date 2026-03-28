package com.example.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val orderIndex: Int,
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
