package com.example.movies.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.use_case.MovieDetailsUseCase
import com.example.movies.domain.use_case.RefreshMovieDetailsUseCase
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
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val refreshMovieDetailsUseCase: RefreshMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val movieId: Int? = savedStateHandle["movieId"]
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _detailsUIState.update { it.copy(isLoading = false, error = throwable.message) }
    }

    private val _detailsUIState = MutableStateFlow(DetailsUiState())
    val detailsUIState = _detailsUIState.asStateFlow()

    init {
        getMovieDetails()
        refreshMovieDetails()
    }

    private fun getMovieDetails() {
        movieId ?: return
        movieDetailsUseCase(movieId).onEach { details ->
            _detailsUIState.update { it.copy(details = details) }
        }.launchIn(viewModelScope)
    }

    fun refreshMovieDetails() {
        movieId ?: return
        viewModelScope.launch(coroutineExceptionHandler) {
            _detailsUIState.update { it.copy(isLoading = true) }
            refreshMovieDetailsUseCase(movieId)
            _detailsUIState.update { it.copy(isLoading = false) }
        }
    }

    fun toggleFavorite() {
        movieId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            toggleFavoriteUseCase(movieId)
        }
    }
}