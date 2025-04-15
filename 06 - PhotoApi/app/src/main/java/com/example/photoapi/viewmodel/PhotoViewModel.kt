package com.example.photoapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoapi.model.DataState
import com.example.photoapi.model.api.response.UnsplashSearchResponse
import com.example.photoapi.model.repository.UnsplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val repositoryApi: UnsplashRepository
) : ViewModel() {

    private val _photos = MutableLiveData<DataState<UnsplashSearchResponse>>()
    val photos: LiveData<DataState<UnsplashSearchResponse>> get() = _photos

    fun getPhotos(search: String) {
        _photos.value = DataState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryApi.getPhotos(search)
            _photos.postValue(result)
        }
    }

}