package com.example.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.photogallery.model.Fact
import com.example.photogallery.model.repository.CatFactRepository
import kotlinx.coroutines.launch

class GalleryFragmentViewModel(
    private val catRepository: CatFactRepository
): ViewModel() {

    private val _factsPager = MutableLiveData<PagingData<Fact>>()
    val factsPager: LiveData<PagingData<Fact>> get() = _factsPager

    init {
        tryToLoadFacts()
    }

    fun tryToLoadFacts() {
        viewModelScope.launch {
            catRepository.getCatFactsPage()
                .cachedIn(viewModelScope)
                .collect {
                    _factsPager.postValue(it)
                }
        }
    }
}