package com.example.movies.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.use_case.FavoritesUseCase
import com.example.movies.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    favoritesUseCase: FavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val favoriteUIState = favoritesUseCase()
        .map { FavoritesUiState(favorites = it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, FavoritesUiState())

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleFavoriteUseCase(movieId)
        }
    }
}