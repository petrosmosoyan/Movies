package com.example.movies.data.remote.dto.details

import com.google.gson.annotations.SerializedName

class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso: String? = null,
    @SerializedName("name")
    val name: String? = null
)