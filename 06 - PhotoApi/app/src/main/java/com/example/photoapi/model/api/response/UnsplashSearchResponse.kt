package com.example.photoapi.model.api.response

import com.google.gson.annotations.SerializedName

data class UnsplashSearchResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val result: List<UnsplashItemPhoto>
)