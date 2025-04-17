package com.example.photoapi.model.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object QueryPreferences {

    private const val MY_SHARED_PREFERENCES = "mySharedPreferences"
    private const val PREF_IS_POLLING = "isPolling"

    private lateinit var myPrefs: SharedPreferences

    fun init(context: Context) {
        myPrefs = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun isPolling(): Boolean {
        return myPrefs.getBoolean(PREF_IS_POLLING, false)
    }

    fun setPolling(active: Boolean) {
        myPrefs.edit {
            putBoolean(PREF_IS_POLLING, active)
        }
    }
}