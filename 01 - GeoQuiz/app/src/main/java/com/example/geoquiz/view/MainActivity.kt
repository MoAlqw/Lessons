package com.example.geoquiz.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.Buttons
import com.example.geoquiz.R
import com.example.geoquiz.contracts.AnswerResultContract
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnFalse: Button
    private lateinit var btnTrue: Button
    private lateinit var btnPastQuestion: ImageButton
    private lateinit var btnNextQuestion: ImageButton
    private lateinit var btnCheat: Button
    private val viewModel: MainViewModel by viewModels()

    private val startForResult = registerForActivityResult(AnswerResultContract()) {
        viewModel.updateCheatVariable(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        btnFalse = binding.btnFalse
        btnTrue = binding.btnTrue
        btnPastQuestion = binding.btnPastQuestion
        btnNextQuestion = binding.btnNextQuestion
        btnCheat = binding.btnCheat

        viewModel.isGameOver.observe(this) {
            if (it) showResult()
        }

        viewModel.currentStateButton.observe(this) {
            btnFalse.isEnabled = it
            btnTrue.isEnabled = it
        }

        viewModel.currentQuestion.observe(this) {
            binding.tvQuestion.text = it
        }

        btnFalse.setOnClickListener { showAnswerResult(false) }
        btnTrue.setOnClickListener { showAnswerResult(true) }
        btnPastQuestion.setOnClickListener { switchQuestion(Buttons.PAST) }
        btnNextQuestion.setOnClickListener { switchQuestion(Buttons.NEXT) }
        btnCheat.setOnClickListener { startAnswerActivity() }
    }

    private fun startAnswerActivity() {
        if (!viewModel.dataIsEmpty) startForResult.launch(viewModel.getAnswerCurrentQuestion)
        else Toast.makeText(this, R.string.data_are_not_loaded, Toast.LENGTH_SHORT).show()
    }

    private fun showResult() {
        val result = viewModel.gameOver()
        Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
    }

    private fun switchQuestion(button: Buttons) {
        viewModel.buttonQuestion(button)
    }

    private fun showAnswerResult(btnValue: Boolean) {
        val message = viewModel.checkAnswer(btnValue)
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()

        // Check Game Over
        viewModel.checkGameOver()
    }

}