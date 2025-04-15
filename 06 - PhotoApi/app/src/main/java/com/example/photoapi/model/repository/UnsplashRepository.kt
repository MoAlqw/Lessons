package com.example.photoapi.model.repository

import com.example.photoapi.model.DataState
import com.example.photoapi.model.api.UnsplashApi
import com.example.photoapi.model.api.UnsplashAuthInterceptor
import com.example.photoapi.model.api.response.UnsplashSearchResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnsplashRepository {

    private val unsplashApi: UnsplashApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(UnsplashAuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        unsplashApi = retrofit.create(UnsplashApi::class.java)
    }

    suspend fun getPhotos(message: String): DataState<UnsplashSearchResponse> {
        try {
            val response = unsplashApi.searchPhotos(message)
            if (response.isSuccessful && response.body() != null) {
                return DataState.Success(response.body()!!)
            }
            return DataState.Error(response.message())
        } catch (e: Exception) {
            return DataState.Error(e.message)
        }
    }

}