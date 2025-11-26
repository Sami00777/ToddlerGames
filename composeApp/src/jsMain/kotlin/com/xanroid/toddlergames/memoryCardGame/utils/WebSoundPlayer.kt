package com.xanroid.toddlergames.memoryCardGame.utils

import org.w3c.dom.Audio

class WebSoundPlayer : SoundPlayer {

    private var currentAudio: Audio? = null

    override suspend fun playSound(resourcePath: String) {
        try {
            currentAudio?.pause()
            currentAudio = null

            val audio = Audio("/files/$resourcePath") // path relative to web/resources
            audio.play() // returns JS Promise
            currentAudio = audio
        } catch (e: dynamic) {
            console.log(e)
        }
    }

    override fun release() {
        currentAudio?.pause()
        currentAudio = null
    }
}
