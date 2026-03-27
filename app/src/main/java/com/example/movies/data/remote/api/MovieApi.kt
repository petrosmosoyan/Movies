package com.example.movies.data.remote.api

import com.example.movies.data.remote.dto.MovieResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0YzcwOTcwNWY3MDc0ZWU1MjlkODdhMTc4ZjM4NmUxOSIsIm5iZiI6MTc3NDU5MTU3MC4xNzQsInN1YiI6IjY5YzYxZTUyMzk2NDYxMWNkMTA4YTkwYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DcVM-g_C9KDCiUMDj-FVlEpT10xkaJOh1FMCtcWpdLk"
    )
    @GET("3/movie/top_rated")
    suspend fun getMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse
}