package com.example.photogallery.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.photogallery.databinding.ActivityPhotoGalleryBinding
import com.example.photogallery.view.fragment.PhotoGalleryFragment

class PhotoGalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoGalleryBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val currentFragment = savedInstanceState == null

        if (currentFragment) {
            supportFragmentManager.commit {
                add(binding.fragmentContainer.id, PhotoGalleryFragment.newInstance())
            }
        }
    }
}