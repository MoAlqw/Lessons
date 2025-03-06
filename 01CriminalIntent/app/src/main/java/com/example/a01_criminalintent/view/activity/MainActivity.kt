package com.example.a01_criminalintent.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a01_criminalintent.R
import com.example.a01_criminalintent.databinding.ActivityMainBinding
import com.example.a01_criminalintent.view.fragment.CrimeFragment
import com.example.a01_criminalintent.view.fragment.CrimeListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
         if (currentFragment == null) {
             val crimeFragment = CrimeListFragment.newInstance()
             supportFragmentManager
                 .beginTransaction()
                 .add(R.id.fragment_container, crimeFragment)
                 .commit()
         }

    }
}