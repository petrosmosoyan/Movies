package com.example.movies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movies.domain.use_case.MoviesUseCase
import com.example.movies.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    moviesUseCase: MoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    val moviesPagingData = moviesUseCase().cachedIn(viewModelScope)

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) { toggleFavoriteUseCase(movieId) }
    }
}