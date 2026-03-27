package com.example.movies.domain.use_case

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = combine(
        movieRepository.getMovies(),
        movieRepository.getFavorites()
    ) { movies, favorites ->
        movies.map { movie ->
            movie.copy(isFavorite = favorites.contains(movie.id))
        }
    }

}