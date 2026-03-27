package com.example.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val movieDtos: List<MovieDto>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)