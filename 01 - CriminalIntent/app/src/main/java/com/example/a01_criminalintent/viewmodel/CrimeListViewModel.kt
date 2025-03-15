package com.example.a01_criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.model.repository.CrimeRepository
import kotlinx.coroutines.launch

class CrimeListViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Crime>>()
    val data: LiveData<List<Crime>> get() = _data

    private val crimeRepository = CrimeRepository.get()

    init {
        viewModelScope.launch {
            val crimes = crimeRepository.getCrimes()
            _data.postValue(crimes)
        }
    }

}