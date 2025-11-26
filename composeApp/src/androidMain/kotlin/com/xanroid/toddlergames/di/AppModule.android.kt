package com.xanroid.toddlergames.di

import com.xanroid.toddlergames.memoryCardGame.utils.AndroidSoundPlayer
import com.xanroid.toddlergames.memoryCardGame.utils.SoundPlayer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SoundPlayer> {
        AndroidSoundPlayer(get()) // Koin will inject Context automatically
    }
}