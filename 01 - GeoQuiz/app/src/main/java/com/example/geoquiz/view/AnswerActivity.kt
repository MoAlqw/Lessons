package com.example.geoquiz.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityAnswerBinding

class AnswerActivity : AppCompatActivity() {

    private lateinit var tvAnswer: TextView
    private lateinit var binding: ActivityAnswerBinding
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerBinding.inflate(layoutInflater).also { setContentView(it.root) }
        tvAnswer = binding.tvAnswer

        val answer = intent.getBooleanExtra(CHEAT_ANSWER, false)

        flag = savedInstanceState?.getBoolean(WAS_CHEAT) ?: false
        if (flag) {
            wasCheat(answer)
        }

        binding.btnCheat.setOnClickListener {
            wasCheat(answer)
        }
    }

    private fun wasCheat(input: Boolean) {
        flag = true
        setResult(Activity.RESULT_OK, Intent().putExtra(CHEAT_ANSWER, flag))
        tvAnswer.setText( when {
            input -> R.string.true_string
            else -> R.string.false_string
        } )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(WAS_CHEAT, flag)
    }

    companion object {
        const val CHEAT_ANSWER = "0503"
        const val WAS_CHEAT = "2004"
    }
}