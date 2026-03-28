package com.example.movies.data.remote.dto.details

import com.google.gson.annotations.SerializedName

class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String? = null,
    @SerializedName("iso_639_1")
    val iso: String? = null,
    @SerializedName("name")
    val name: String? = null
)