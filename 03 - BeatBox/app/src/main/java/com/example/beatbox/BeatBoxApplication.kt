package com.example.beatbox

import android.app.Application
import com.example.beatbox.model.repository.SoundRepository

class BeatBoxApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SoundRepository.initialize(this)
    }

}