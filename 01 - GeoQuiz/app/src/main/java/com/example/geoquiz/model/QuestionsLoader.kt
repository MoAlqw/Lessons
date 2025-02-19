package com.example.geoquiz.model


// Imitation API

class QuestionsLoader {

    companion object {
        fun load(): List<Question> {
            return listOf(
                Question("If configuration has been changed ViewModel save data.", true),
                Question("Activity is standard of Android SDK.", true),
                Question("Activity is easier than Fragment.", false)
            )
        }
    }
}