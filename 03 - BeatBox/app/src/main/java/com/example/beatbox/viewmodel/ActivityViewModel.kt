package com.example.beatbox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beatbox.model.BeatBox
import com.example.beatbox.model.Sound
import com.example.beatbox.model.repository.SoundRepository
import kotlinx.coroutines.launch

class ActivityViewModel: ViewModel() {

    private val repository = SoundRepository.get()
    private val beatBox = BeatBox()

    private val _sounds = MutableLiveData<List<Sound>>()
    val sounds: LiveData<List<Sound>> get() = _sounds
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            val tryToLoadSounds = repository.loadSounds()
            tryToLoadSounds.forEach {
                beatBox.load(it, repository.assets)
            }
            _sounds.postValue(tryToLoadSounds)
            _isLoading.postValue(false)
        }
    }

    fun playSound(sound: Sound) {
        beatBox.play(sound)
    }

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }

}