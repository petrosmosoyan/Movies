package com.example.movies.data.remote.dto.details

import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)