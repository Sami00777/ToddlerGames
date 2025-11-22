package com.xanroid.toddlergames.di

import com.xanroid.toddlergames.memoryCardGame.utils.IosSoundPlayer
import com.xanroid.toddlergames.memoryCardGame.utils.SoundPlayer
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::IosSoundPlayer).bind<SoundPlayer>()
}