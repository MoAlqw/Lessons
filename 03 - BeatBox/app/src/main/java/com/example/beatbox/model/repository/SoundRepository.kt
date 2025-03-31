package com.example.beatbox.model.repository

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.beatbox.model.Sound
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SoundRepository private constructor(context: Context) {

    val assets: AssetManager = context.assets

    suspend fun loadSounds(): List<Sound> = withContext(Dispatchers.IO) {
        val soundNames: Array<String>
        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: IOException) {
            Log.e(TAG, "Could not list assets", e)
            return@withContext emptyList()
        }
        return@withContext soundNames.map { Sound("$SOUNDS_FOLDER/$it") }
    }

    companion object {
        private const val TAG = "BeatBox"
        private const val SOUNDS_FOLDER = "sample_sounds"

        private var INSTANCE: SoundRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SoundRepository(context)
            }
        }

        fun get(): SoundRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }

}