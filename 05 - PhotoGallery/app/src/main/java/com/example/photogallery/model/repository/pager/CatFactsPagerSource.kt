package com.example.photogallery.model.repository.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.photogallery.model.Fact
import com.example.photogallery.model.api.CatFactApi

class CatFactsPagerSource(
    private val catApi: CatFactApi
): PagingSource<Int, Fact>() {

    override fun getRefreshKey(state: PagingState<Int, Fact>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Fact> {
        val page = params.key ?: 1
        return try {
            val response = catApi.getFacts(page)
            val data = response.data
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page -1,
                nextKey = if (response.nextPageUrl == null)  null else page + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}