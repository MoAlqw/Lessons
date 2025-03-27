package com.example.a01_criminalintent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a01_criminalintent.model.repository.CrimeRepository
import com.example.a01_criminalintent.model.room.Crime
import com.example.a01_criminalintent.view.fragment.CrimeFragment
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class CrimeFragmentViewModel(private val state: SavedStateHandle): ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    private val _currentCrime = MutableLiveData<Crime>()
    val currentCrime: LiveData<Crime> get() = _currentCrime
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _photoFile = MutableLiveData<File>()
    val photoFile: LiveData<File> get() = _photoFile

    init {
        viewModelScope.launch {
            val crimeId: UUID = state[CrimeFragment.ARG_CRIME_ID] ?: throw IllegalArgumentException("crimeId is missing")
            val crime = crimeRepository.getCrime(crimeId) ?: Crime()
            _currentCrime.postValue(crime)
            _photoFile.postValue(getPhotoFile(crime))
            _isLoading.postValue(false)
        }
    }

    private fun getPhotoFile(crime: Crime?): File {
        if (crime != null) return crimeRepository.getPhotoCrime(crime)
        else throw IllegalStateException("Crime not uploaded")
    }

    fun updateCrime(update: (Crime) -> Crime) {
        val crime = _currentCrime.value ?: return
        _currentCrime.value = update(crime)
    }

    fun saveCrime() {
        viewModelScope.launch {
            val crime = _currentCrime.value ?: return@launch
            crimeRepository.updateCrime(crime)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                CrimeFragmentViewModel(savedStateHandle)
            }
        }
    }

}