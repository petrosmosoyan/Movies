package com.example.movies.data.mapper

import com.example.movies.data.local.entity.MovieEntity
import com.example.movies.data.remote.dto.details.DetailsDto
import com.example.movies.data.remote.dto.movies.MovieDto
import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Movie

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
)

fun DetailsDto.toEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    budget = budget,
    revenue = revenue,
    homePage = homepage
)

fun MovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    posterPath = "https://image.tmdb.org/t/p/w500$posterPath",
    releaseDate = releaseDate,
)

fun MovieEntity.toDetails(): Details = Details(
    title = title,
    overview = overview,
    posterPath = "https://image.tmdb.org/t/p/w500$posterPath",
    backdropPath = "https://image.tmdb.org/t/p/w500$backdropPath",
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    budget = budget,
    revenue = revenue,
    homePage = homePage,
)