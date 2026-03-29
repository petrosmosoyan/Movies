package com.example.movies.data.mapper

import com.example.movies.data.local.entity.MovieEntity
import com.example.movies.data.local.model.MovieWithFavorite
import com.example.movies.data.remote.dto.details.DetailsDto
import com.example.movies.data.remote.dto.movies.MovieDto
import com.example.movies.domain.model.Details
import com.example.movies.domain.model.Favorite
import com.example.movies.domain.model.Movie

fun MovieDto.toEntity(orderIndex: Int): MovieEntity = MovieEntity(
    id = id,
    orderIndex = orderIndex,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
)

fun DetailsDto.toEntity(orderIndex: Int) = MovieEntity(
    id = id,
    orderIndex = orderIndex,
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

fun MovieWithFavorite.toMovie(): Movie = Movie(
    id = movie.id,
    title = movie.title,
    posterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
    releaseDate = movie.releaseDate,
    isFavorite = isFavorite
)

fun MovieWithFavorite.toFavorite(): Favorite = Favorite(
    id = movie.id,
    title = movie.title,
    posterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
    releaseDate = movie.releaseDate,
    voteAverage = movie.voteAverage
)

fun MovieWithFavorite.toDetails(): Details = Details(
    title = movie.title,
    overview = movie.overview,
    posterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
    backdropPath = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
    releaseDate = movie.releaseDate,
    voteAverage = movie.voteAverage,
    budget = movie.budget,
    revenue = movie.revenue,
    homePage = movie.homePage,
    isFavorite = isFavorite
)