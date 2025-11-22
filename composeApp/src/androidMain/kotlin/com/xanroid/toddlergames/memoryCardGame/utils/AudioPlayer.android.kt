package com.xanroid.toddlergames.memoryCardGame.utils

import android.content.Context
import android.media.MediaPlayer
import toddlergames.composeapp.generated.resources.Res
import java.io.File

class AndroidSoundPlayer(private val context: Context): SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    override suspend fun playSound(resourcePath: String) {
        try {
            // Release previous player if exists
            mediaPlayer?.release()

            // Read bytes from Compose Resources
            val bytes = Res.readBytes("files/$resourcePath")

            val tempFile = File.createTempFile("sound_", ".mp3", context.cacheDir)
            tempFile.writeBytes(bytes)

            mediaPlayer = MediaPlayer().apply {
                setDataSource(tempFile.absolutePath)
                prepare()
                start()
                setOnCompletionListener {
                    it.release()
                    tempFile.delete()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

