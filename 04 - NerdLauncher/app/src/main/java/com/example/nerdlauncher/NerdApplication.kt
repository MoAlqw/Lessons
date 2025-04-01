package com.example.nerdlauncher

import android.app.Application
import com.example.nerdlauncher.model.repository.NerdRepository

class NerdApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        NerdRepository.initialize(this)
    }

}