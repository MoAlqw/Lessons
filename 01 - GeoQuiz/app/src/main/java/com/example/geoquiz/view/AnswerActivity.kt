package com.example.geoquiz.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityAnswerBinding

class AnswerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val answer = intent.getBooleanExtra(CHEAT_ANSWER, false).toString()
        TODO("После смены конфигурации не запоминает, что юзер нажимал на кнопку")
        binding.btnCheat.setOnClickListener {
            wasClick(answer)
        }
    }

    private fun wasClick(input: String) {
        setResult(Activity.RESULT_OK, Intent().putExtra(CHEAT_ANSWER, true))
        Toast.makeText(this@AnswerActivity, input, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val CHEAT_ANSWER = "0503"
    }
}