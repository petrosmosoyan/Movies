package com.example.movies.domain.use_case

import com.example.movies.domain.repository.FavoritesRepository
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(movieId: Int) = combine(
        movieRepository.getMovieDetails(movieId),
        favoritesRepository.getFavorites()
    ) { details, isFavorite ->
        details.copy(isFavorite = isFavorite.contains(movieId))
    }
}