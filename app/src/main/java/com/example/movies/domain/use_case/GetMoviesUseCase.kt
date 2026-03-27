package com.example.movies.domain.use_case

import com.example.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getMovies()
}