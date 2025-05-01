package com.example.photoapi.view.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.photoapi.R
import com.example.photoapi.databinding.ActivityPhotoPageBinding
import com.example.photoapi.view.fragment.PhotoPageFragment

class PhotoPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoPageBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(binding.fragmentContainer.id)
        if (currentFragment == null) {
            val fragment = PhotoPageFragment.newInstance(intent.data!!)
            fm.commit {
                add(R.id.fragment_container, fragment)
            }
        }
    }

    companion object {
        fun newIntent(context: Context, photoPageUri: Uri): Intent {
            return Intent(context, PhotoPageActivity::class.java).apply {
                data = photoPageUri
            }
        }
    }
}