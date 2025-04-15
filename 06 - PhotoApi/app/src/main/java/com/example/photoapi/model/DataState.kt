package com.example.photoapi.model

sealed class DataState<T> {
    data class Success<T>(val data: T): DataState<T>()
    data class Error<T>(val message: String?): DataState<T>()
    class Loading<T>: DataState<T>()
}