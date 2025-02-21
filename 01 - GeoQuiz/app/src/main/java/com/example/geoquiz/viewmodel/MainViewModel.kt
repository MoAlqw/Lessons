package com.example.geoquiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquiz.Buttons
import com.example.geoquiz.model.Question
import com.example.geoquiz.model.QuestionsLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private var index = 0
    private var data: List<Question> = emptyList()
    private val userAnswers = mutableMapOf<Int, Int?>()

    private val _isGameOver = MutableLiveData(false)
    val isGameOver: LiveData<Boolean> get() = _isGameOver

    private val _currentStateButton = MutableLiveData(false)
    val currentStateButton: LiveData<Boolean> get() = _currentStateButton

    private val _currentQuestion = MutableLiveData<String>()
    val currentQuestion: LiveData<String> get() = _currentQuestion

    init {
        // Maybe it would have been worth using a repository here
        viewModelScope.launch {
            _currentQuestion.value = "Loading questions.."
            val loadedData = withContext(Dispatchers.IO) {
                QuestionsLoader.load()
            }

            data = loadedData

            userAnswers.putAll(data.indices.associateWith { null })
            _currentQuestion.value = data.firstOrNull()?.question ?: "No questions.."
            _currentStateButton.value = true
        }
    }

    fun checkGameOver() {
        if (userAnswers.values.all { it != null } && data.isNotEmpty()) _isGameOver.value = true
    }

    fun gameOver(): String {
        val correctAnswers = userAnswers.values.sumOf { it ?: 0 }
        return "Your result is $correctAnswers/${data.size}"
    }

    fun checkAnswer(answer: Boolean): Boolean {
        if (data.isNotEmpty()) {
            val result = data.getOrNull(index)?.answer == answer
            userAnswers[index] = if (result) 1 else 0
            // Update buttons
            _currentStateButton.value = false

            return result
        }
        return false
    }

    fun buttonQuestion(nameButton: Buttons) {
        if (data.isNotEmpty()) {
            val dataSize = data.size
            index = when(nameButton) {
                Buttons.NEXT -> (index + 1) % dataSize
                Buttons.PAST -> (index-1+dataSize) % dataSize
            }
            _currentQuestion.value = data[index].question

            // Update buttons
            _currentStateButton.value = userAnswers[index] == null
        }
    }
}