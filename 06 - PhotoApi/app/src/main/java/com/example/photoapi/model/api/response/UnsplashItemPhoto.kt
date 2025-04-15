package com.example.photoapi.model.api.response

import com.google.gson.annotations.SerializedName

data class UnsplashItemPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val urls: Urls
)

data class Urls(
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String
)

