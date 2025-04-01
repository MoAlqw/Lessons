package com.example.nerdlauncher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nerdlauncher.model.AppInfo
import com.example.nerdlauncher.model.repository.NerdRepository
import kotlinx.coroutines.launch

class NerdViewModel: ViewModel() {

    private val repository = NerdRepository.get()

    private val _activities = MutableLiveData<List<AppInfo>>()
    val activities: LiveData<List<AppInfo>> get() = _activities
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            val loadActivities = repository.loadActivities()
            _activities.postValue(loadActivities)
            _isLoading.postValue(false)
        }
    }

}