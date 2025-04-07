package com.example.photogallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photogallery.model.repository.CatFactRepository

class CatFactViewModelFactory(
    private val catFactRepository: CatFactRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryFragmentViewModel(catFactRepository) as T
    }

}