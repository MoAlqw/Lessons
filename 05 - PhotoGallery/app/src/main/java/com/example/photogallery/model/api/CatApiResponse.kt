package com.example.photogallery.model.api

import com.example.photogallery.model.Fact

data class CatApiResponse(
    val current_page: Double,
    val data: List<Fact>,
    val first_page_url: String?,
    val last_page_url: String?,
    val next_page_url: String?,
    val prev_page_url: String?,
    val total: Double
)
