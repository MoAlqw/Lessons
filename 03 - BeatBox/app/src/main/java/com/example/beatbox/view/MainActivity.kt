package com.example.beatbox.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.model.Sound
import com.example.beatbox.model.adapter.Callbacks
import com.example.beatbox.model.adapter.RecyclerAdapter
import com.example.beatbox.viewmodel.ActivityViewModel

class MainActivity : AppCompatActivity(), Callbacks {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recycler: RecyclerView
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        recycler = binding.recyclerView

        viewModel.sounds.observe(this) {
            recycler.layoutManager = GridLayoutManager(this@MainActivity, 3)
            recycler.adapter = RecyclerAdapter(it, this@MainActivity)
        }

        viewModel.isLoading.observe(this) {
            if (!it) {
                binding.progressBar.visibility = View.GONE
                recycler.visibility = View.VISIBLE
            }
        }
    }

    override fun clicked(sound: Sound) {
        viewModel.playSound(sound)
    }

}