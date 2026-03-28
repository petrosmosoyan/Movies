package com.example.movies.domain.use_case

import com.example.movies.domain.repository.FavoritesRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.toggleFavorite(movieId)
    }
}