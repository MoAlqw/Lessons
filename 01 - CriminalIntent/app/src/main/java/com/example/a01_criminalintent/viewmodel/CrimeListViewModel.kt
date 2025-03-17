package com.example.a01_criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.model.repository.CrimeRepository

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val data: LiveData<List<Crime>> = crimeRepository.getCrimes().asLiveData()

}