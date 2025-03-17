package com.example.a01_criminalintent.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.a01_criminalintent.R
import com.example.a01_criminalintent.databinding.ActivityMainBinding
import com.example.a01_criminalintent.model.callback.Callbacks
import com.example.a01_criminalintent.view.fragment.CrimeFragment
import com.example.a01_criminalintent.view.fragment.CrimeListFragment
import java.util.UUID

class MainActivity : AppCompatActivity(), Callbacks {

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
            val crimeListFragment = CrimeListFragment.newInstance()
            supportFragmentManager.commit {
                add(R.id.fragment_container, crimeListFragment)
            }
        }
    }

    override fun onCrimeSelected(crimeId: UUID) {
        val fragment = CrimeFragment.newInstance(crimeId)
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
}