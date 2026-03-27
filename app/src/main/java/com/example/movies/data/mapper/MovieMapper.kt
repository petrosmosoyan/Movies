package com.example.movies.data.mapper

import com.example.movies.data.local.entity.MovieEntity
import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.domain.model.Movie
import kotlin.Boolean

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    poster = "https://image.tmdb.org/t/p/w500$posterPath"
)