package com.example.movies.domain.use_case

import com.example.movies.domain.repository.MovieRepository
import javax.inject.Inject

class RefreshMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke() {
        repository.refreshMovies()
    }
}