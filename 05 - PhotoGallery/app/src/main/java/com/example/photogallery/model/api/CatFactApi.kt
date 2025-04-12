package com.example.photogallery.model.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactApi {

    @GET("facts")
    suspend fun getFacts(@Query("page") page: Int): CatApiResponse

}
