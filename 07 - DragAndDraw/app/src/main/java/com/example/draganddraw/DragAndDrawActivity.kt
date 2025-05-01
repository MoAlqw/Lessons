package com.example.draganddraw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.draganddraw.databinding.ActivityDragAndDrawBinding

class DragAndDrawActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDragAndDrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragAndDrawBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}