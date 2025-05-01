package com.example.sunset

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sunset.databinding.ActivitySunsetBinding

class SunsetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySunsetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySunsetBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}