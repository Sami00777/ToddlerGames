package com.xanroid.toddlergames.memoryCardGame.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import platform.Foundation.*

class IosSoundPlayer(): SoundPlayer {
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun playSound(resourcePath: String) {
        try {
            val bundle = NSBundle.mainBundle
            val path = bundle.pathForResource(resourcePath.substringBeforeLast("."), "mp3")

            if (path != null) {
                val url = NSURL.fileURLWithPath(path)
                audioPlayer = AVAudioPlayer(contentsOfURL = url, error = null)
                audioPlayer?.play()
            }
        } catch (e: Exception) {
            println("Error playing sound: ${e.message}")
        }
    }

    override fun release() {
        audioPlayer?.stop()
        audioPlayer = null
    }
}
