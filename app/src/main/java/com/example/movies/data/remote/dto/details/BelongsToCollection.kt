package com.example.movies.data.remote.dto.details

import com.google.gson.annotations.SerializedName

class BelongsToCollection(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null
)