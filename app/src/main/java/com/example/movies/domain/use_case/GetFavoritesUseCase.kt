package com.example.movies.domain.use_case

import com.example.movies.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke() = repository.getFavorites()
}