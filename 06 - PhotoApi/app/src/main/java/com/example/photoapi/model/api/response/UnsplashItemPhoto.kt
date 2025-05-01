package com.example.photoapi.model.api.response

import com.google.gson.annotations.SerializedName

data class UnsplashItemPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val urls: Url,
    @SerializedName("links") val link: Link
)

data class Url(
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String
)

data class Link(
    @SerializedName("html") val html: String
)

