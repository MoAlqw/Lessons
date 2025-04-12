package com.example.photogallery.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.photogallery.model.Fact
import com.example.photogallery.model.api.CatFactApi
import com.example.photogallery.model.repository.pager.CatFactsPagerSource
import kotlinx.coroutines.flow.Flow
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

    fun getCatFactsPage(): Flow<PagingData<Fact>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CatFactsPagerSource(catFactApi) }
        ).flow
    }

}