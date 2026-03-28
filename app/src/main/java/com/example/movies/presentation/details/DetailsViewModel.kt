package com.example.movies.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int? = savedStateHandle["movieId"]

    init {
        println("Movie ID in ViewModel: $movieId")
    }

    fun a() {
    }
}