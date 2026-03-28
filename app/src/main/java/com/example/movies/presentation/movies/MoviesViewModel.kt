package com.example.movies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.use_case.MoviesUseCase
import com.example.movies.domain.use_case.RefreshMoviesUseCase
import com.example.movies.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val refreshMoviesUseCase: RefreshMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _moviesUIState = MutableStateFlow(MoviesUiState())
    val moviesUIState = _moviesUIState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _moviesUIState.update { it.copy(isLoading = false, error = throwable.message) }
    }

    init {
        getMovies()
        refreshMovies()
    }

    fun getMovies() {
        moviesUseCase()
            .onEach { movies ->
                _moviesUIState.update { it.copy(movies = movies) }
            }
            .launchIn(viewModelScope)
    }

    fun refreshMovies() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _moviesUIState.update { it.copy(isLoading = true) }
            refreshMoviesUseCase()
            _moviesUIState.update { it.copy(isLoading = false) }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) { toggleFavoriteUseCase(movieId) }
    }
}