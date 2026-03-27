package com.example.movies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.use_case.GetMoviesUseCase
import com.example.movies.domain.use_case.RefreshMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val refreshMoviesUseCase: RefreshMoviesUseCase
) : ViewModel() {

    private val _moviesUIState = MutableStateFlow(MoviesUiState())
    val moviesUIState = _moviesUIState.asStateFlow()

    init {
        getMovies()
        refreshMovies()
    }

    fun getMovies() {
        getMoviesUseCase()
            .onEach { movies ->
                _moviesUIState.update { uiState -> uiState.copy(movies = movies) }
            }
            .launchIn(viewModelScope)
    }

    fun refreshMovies() {
        viewModelScope.launch {
            try {
                _moviesUIState.update { it.copy(isLoading = true) }
                refreshMoviesUseCase()
                _moviesUIState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                val msg = e.message
                _moviesUIState.update { it.copy(isLoading = false, error = msg) }
            }
        }
    }
}