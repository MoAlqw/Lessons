package com.example.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.model.DataState
import com.example.photogallery.model.Fact
import com.example.photogallery.model.repository.CatFactRepository
import kotlinx.coroutines.launch

class GalleryFragmentViewModel(
    private val catRepository: CatFactRepository
): ViewModel() {

    private val _factCat = MutableLiveData<DataState<List<Fact>>>()
    val factCat: LiveData<DataState<List<Fact>>> get() = _factCat

    init {
        tryToLoadFacts()
    }

    fun tryToLoadFacts() {
        viewModelScope.launch {
            _factCat.value = DataState.Loading
            val tryResponse = catRepository.getCatFact()
            _factCat.postValue(tryResponse)
        }
    }
}