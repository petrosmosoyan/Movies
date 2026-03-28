package com.example.movies.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.use_case.FavoritesUseCase
import com.example.movies.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _favoriteUIState = MutableStateFlow(FavoritesUiState())
    val favoriteUIState = _favoriteUIState.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        favoritesUseCase().onEach { favorites ->
            _favoriteUIState.update { state ->
                state.copy(favorites = favorites)
            }
        }.launchIn(viewModelScope)
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleFavoriteUseCase(movieId)
        }
    }
}