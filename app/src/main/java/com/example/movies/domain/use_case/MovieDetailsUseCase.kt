package com.example.movies.domain.use_case

import com.example.movies.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(movieId: Int) = movieRepository.getMovieDetails(movieId)
}