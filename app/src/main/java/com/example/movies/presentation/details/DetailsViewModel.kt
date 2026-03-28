package com.example.movies.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.common.Resource
import com.example.movies.domain.use_case.MovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailsUseCase: MovieDetailsUseCase
) : ViewModel() {

    private val movieId: Int? = savedStateHandle["movieId"]

    private val _detailsUIState = MutableStateFlow(DetailsUiState())
    val detailsUIState = _detailsUIState.asStateFlow()

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        movieId ?: return
        movieDetailsUseCase(movieId).onEach { details ->
            when (details) {
                is Resource.Success -> _detailsUIState.update {
                    it.copy(isLoading = true)
                }

                is Resource.Loading -> _detailsUIState.update {
                    it.copy(isLoading = true)
                }

                else -> {}
            }
        }.catch {
            val msg = it.message
            _detailsUIState.value = _detailsUIState.value.copy(error = msg)
        }.launchIn(viewModelScope)
    }
}