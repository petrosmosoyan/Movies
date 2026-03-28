package com.example.movies.data.remote.dto.details

import com.google.gson.annotations.SerializedName

class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso3166: String,
    @SerializedName("name")
    val name: String
)