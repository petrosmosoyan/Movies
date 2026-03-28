package com.example.movies.domain.use_case

import com.example.movies.common.Resource
import com.example.movies.domain.model.Details
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(movieId: Int) : Flow<Resource<Details>> = flow {
        emit(Resource.Loading())
        val details = movieRepository.getMovieDetails(movieId)
        emit(Resource.Success(details))
    }
}