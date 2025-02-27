package com.example.geoquiz.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.geoquiz.view.AnswerActivity

class AnswerResultContract : ActivityResultContract<Boolean, Boolean>() {
    override fun createIntent(context: Context, input: Boolean): Intent {
        return Intent(context, AnswerActivity::class.java).apply {
            putExtra(AnswerActivity.CHEAT_ANSWER, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return if (resultCode != Activity.RESULT_OK) {
            false
        } else {
            intent?.getBooleanExtra(AnswerActivity.CHEAT_ANSWER, false) == true
        }
    }
}