package com.example.beatbox.model

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BeatBox {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }

    suspend  fun load(sound: Sound, assets: AssetManager) {
        withContext(Dispatchers.IO) {
            val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
            val soundId = soundPool.load(afd, 1)
            sound.soundId = soundId
        }
    }
}