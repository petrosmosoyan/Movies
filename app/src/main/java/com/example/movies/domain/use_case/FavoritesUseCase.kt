package com.example.movies.domain.use_case

import com.example.movies.domain.model.Favorite
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Favorite>> = movieRepository.getFavoriteMovies()
}