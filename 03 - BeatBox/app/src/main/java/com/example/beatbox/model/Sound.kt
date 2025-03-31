package com.example.beatbox.model

class Sound(val assetPath: String, var soundId: Int? = null) {

    val name = assetPath.split("/").last().removeSuffix(WAV)

    companion object {
        private const val WAV = ".wav"
    }
}