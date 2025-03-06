package com.example.a01_criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a01_criminalintent.model.Crime
import com.example.a01_criminalintent.model.LoaderData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrimeListViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Crime>>()
    val data: LiveData<List<Crime>> get() = _data

    init {
        viewModelScope.launch {
            val crimes = withContext(Dispatchers.IO) { LoaderData().load() }
            _data.postValue(crimes)
        }
    }

}