package com.example.photogallery.model

sealed class DataState<out T> {

    data class Success<T>(val data: T): DataState<T>()
    data class Error<T>(val message: String, val code: Int = 404): DataState<T>()
    data object Loading: DataState<Nothing>()

}