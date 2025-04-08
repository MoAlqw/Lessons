package com.example.photogallery.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photogallery.model.repository.CatFactRepository
import com.example.photogallery.viewmodel.GalleryFragmentViewModel

class CatFactViewModelFactory(
    private val catFactRepository: CatFactRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryFragmentViewModel(catFactRepository) as T
    }

}