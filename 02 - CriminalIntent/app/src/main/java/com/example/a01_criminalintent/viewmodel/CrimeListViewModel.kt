package com.example.a01_criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.model.repository.CrimeRepository
import kotlinx.coroutines.launch
import java.util.UUID

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val data: LiveData<List<Crime>> = crimeRepository.getCrimes().asLiveData()

    fun addCrimeAndSelect(newCrime: Crime, onSelected: (UUID) -> Unit) {
        viewModelScope.launch {
            crimeRepository.addCrime(newCrime)
            onSelected(newCrime.id)
        }
    }

}