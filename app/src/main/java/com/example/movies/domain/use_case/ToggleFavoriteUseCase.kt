package com.example.movies.domain.use_case

import com.example.movies.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieId: Int) {
        movieRepository.toggleFavorite(movieId)
    }
}