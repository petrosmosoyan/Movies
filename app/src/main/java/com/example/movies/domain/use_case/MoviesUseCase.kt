package com.example.movies.domain.use_case

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.FavoritesRepository
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Movie>> = combine(
        movieRepository.getMovies(),
        favoritesRepository.getFavoriteIds()
    ) { movies, favorites ->
        movies.map { movie ->
            movie.copy(isFavorite = favorites.contains(movie.id))
        }
    }

}