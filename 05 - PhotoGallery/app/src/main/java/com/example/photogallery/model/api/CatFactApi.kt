package com.example.photogallery.model.api

import retrofit2.Response
import retrofit2.http.GET

interface CatFactApi {

    @GET("/facts")
    suspend fun getFact(): Response<CatApiResponse>

}
