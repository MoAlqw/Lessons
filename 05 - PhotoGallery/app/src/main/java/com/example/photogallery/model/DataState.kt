package com.example.photogallery.model

sealed class DataState<out T>(val url: String = "https://http.cat/") {

    data class Success<T>(val data: T, val code: Int): DataState<T>()
    data class Error<T>(val message: String, val code: Int = 404): DataState<T>()
    data object Loading: DataState<Nothing>()

}