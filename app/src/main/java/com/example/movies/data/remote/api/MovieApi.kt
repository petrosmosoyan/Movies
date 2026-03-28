package com.example.movies.data.remote.api

import com.example.movies.data.remote.dto.details.DetailsDto
import com.example.movies.data.remote.dto.movies.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("3/movie/top_rated")
    suspend fun getMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/movie/{movieId}")
    suspend fun getDetails(@Path("movieId") movieId: Int): DetailsDto
}