package com.example.sunset

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sunset.databinding.ActivitySunsetBinding

class SunsetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySunsetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySunsetBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.scene.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(binding.sun, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.start()
    }
}