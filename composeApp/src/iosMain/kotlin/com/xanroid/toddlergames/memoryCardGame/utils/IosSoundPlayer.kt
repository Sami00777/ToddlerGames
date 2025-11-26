package com.xanroid.toddlergames.memoryCardGame.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL
import toddlergames.composeapp.generated.resources.Res

class IosSoundPlayer(): SoundPlayer {
    private var audioPlayer: AVAudioPlayer? = null

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun playSound(resourcePath: String) {
        val uri = Res.getUri("files/$resourcePath")
        NSURL.URLWithString(URLString = uri)?.let { media ->
            val avAudioPlayer = AVAudioPlayer(media, error = null)
            avAudioPlayer.prepareToPlay()
            avAudioPlayer.play()
        }
    }

    override fun release() {
        audioPlayer?.stop()
        audioPlayer = null
    }
}
