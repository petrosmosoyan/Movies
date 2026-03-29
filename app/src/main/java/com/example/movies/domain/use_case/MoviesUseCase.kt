package com.example.movies.domain.use_case

import androidx.paging.PagingData
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(): Flow<PagingData<Movie>> = movieRepository.getMoviesPaging()
}