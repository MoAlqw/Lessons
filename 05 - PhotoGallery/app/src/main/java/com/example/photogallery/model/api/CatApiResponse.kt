package com.example.photogallery.model.api

import com.example.photogallery.model.Fact
import com.google.gson.annotations.SerializedName

data class CatApiResponse(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("data") val data: List<Fact>,
    @SerializedName("first_page_url") val firstPageUrl: String?,
    @SerializedName("last_page_url") val lastPageUrl: String?,
    @SerializedName("next_page_url") val nextPageUrl: String?,
    @SerializedName("prev_page_url") val prevPageUrl: String?,
    @SerializedName("total") val total: Int
)
