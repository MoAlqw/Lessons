package com.example.photoapi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoapi.R
import com.example.photoapi.model.DataState
import com.example.photoapi.model.api.response.UnsplashSearchResponse
import com.example.photoapi.model.repository.UnsplashRepository
import com.example.photoapi.model.shared_preferences.QueryPreferences
import com.example.photoapi.worker.PollWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val repositoryApi: UnsplashRepository,
    private val application: Application
) : AndroidViewModel(application) {

    private val _photos = MutableLiveData<DataState<UnsplashSearchResponse>>()
    val photos: LiveData<DataState<UnsplashSearchResponse>> get() = _photos

    private val _menuItemTitle = MutableLiveData<Int>()
    val menuItemTitle: LiveData<Int> get() = _menuItemTitle

    fun itemPolling() {
        val isPolling = QueryPreferences.isPolling()

        if (isPolling) {
            PollWorker.cancelUniqueWork(application)
            QueryPreferences.setPolling(false)
        } else {
            PollWorker.enqueue(application)
            QueryPreferences.setPolling(true)
        }
        _menuItemTitle.value = if (!isPolling) R.string.stop_polling else R.string.start_polling
    }

    fun getPhotos(search: String) {
        _photos.value = DataState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryApi.getPhotos(search)

            _photos.postValue(result)
        }
    }

}