package com.example.movies.data.local.model

import androidx.room.Embedded
import com.example.movies.data.local.entity.MovieEntity

data class MovieWithFavorite(
    @Embedded val movie: MovieEntity,
    val isFavorite: Boolean
)