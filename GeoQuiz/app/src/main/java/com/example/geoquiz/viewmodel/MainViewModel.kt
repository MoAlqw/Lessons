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

    private val _currentQuestion = MutableLiveData<String>()
    val currentQuestion: LiveData<String> get() = _currentQuestion

    init {
        // Maybe it would have been worth using a repository here
        viewModelScope.launch {
            data = withContext(Dispatchers.IO) {
                QuestionsLoader.load()
            }

            userAnswers.putAll(data.indices.associateWith { null })
            _currentQuestion.value = data.firstOrNull()?.question ?: "No questions.."
        }
    }

    fun gameOver(): Int {
        return if (null !in userAnswers.values) {
            userAnswers.values.sumOf { it ?: 0 }
        } else {
            -1
        }
    }

    fun enableButton(): Boolean {
        return userAnswers[index] == null
    }

    fun checkAnswer(answer: Boolean): Boolean {
        val result = data.getOrNull(index)?.answer == answer
        userAnswers[index] = if (result) 1 else 0
        return result
    }

    fun buttonQuestion(nameButton: Buttons) {
        if (data.isNotEmpty()) {
            index = when(nameButton) {
                Buttons.NEXT -> (index + 1) % data.size
                Buttons.PAST -> (index-1+data.size) % data.size
            }
            _currentQuestion.value = data[index].question
        }
    }
}