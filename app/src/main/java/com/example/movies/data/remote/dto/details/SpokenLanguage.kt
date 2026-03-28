package com.example.movies.data.remote.dto.details

import com.google.gson.annotations.SerializedName

class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso639: String,
    @SerializedName("name")
    val name: String
)