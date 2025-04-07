package com.example.photogallery.model.repository

import com.example.photogallery.model.DataState
import com.example.photogallery.model.Fact
import com.example.photogallery.model.api.CatFactApi
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatFactRepository {

    private val catFactApi: CatFactApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        catFactApi = retrofit.create(CatFactApi::class.java)
    }

    suspend fun getCatFact(): DataState<List<Fact>> {
        return try {
            val response = catFactApi.getFact()
            val fact = response.body()
            if (response.isSuccessful && fact != null) {
                val data = fact.data
                if (data[0].code == null) {
                    data.forEach { it.code = response.code() }
                }
                DataState.Success(data)
            } else {
                DataState.Error(message = response.message(), code = response.code())
            }
        } catch (e: HttpException) {
            DataState.Error(message = "Something went wrong", code = e.code())
        } catch (e: Exception) {
            DataState.Error(message = "Error")
        }
    }

}