package com.example.photogallery.model.api

import com.example.photogallery.model.Fact
import retrofit2.Response
import retrofit2.http.GET

interface CatFactApi {

    @GET("/fact")
    suspend fun getFact(): Response<Fact>

}
