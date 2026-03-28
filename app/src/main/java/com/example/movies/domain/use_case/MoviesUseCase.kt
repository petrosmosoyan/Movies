package com.example.movies.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
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
    operator fun invoke(): Flow<PagingData<Movie>> = combine(
        movieRepository.getMoviesPaging(),
        favoritesRepository.getFavoriteIds()
    ) { movies, favorites ->
        movies.map { movie ->
            movie.copy(isFavorite = favorites.contains(movie.id))
        }
    }

}