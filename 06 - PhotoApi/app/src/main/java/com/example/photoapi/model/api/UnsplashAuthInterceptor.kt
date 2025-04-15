package com.example.photoapi.model.api

import com.example.photoapi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class UnsplashAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url().newBuilder()
            .addQueryParameter("client_id", BuildConfig.UNSPLASH_API_KEY)
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}