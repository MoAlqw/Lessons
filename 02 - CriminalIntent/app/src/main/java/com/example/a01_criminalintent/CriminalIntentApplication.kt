package com.example.a01_criminalintent

import android.app.Application
import com.example.a01_criminalintent.model.repository.CrimeRepository

class CriminalIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}