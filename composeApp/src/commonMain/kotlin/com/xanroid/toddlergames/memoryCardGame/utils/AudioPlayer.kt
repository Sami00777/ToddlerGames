package com.xanroid.toddlergames.memoryCardGame.utils

interface SoundPlayer {
    suspend fun playSound(resourcePath: String)
    fun release()
}