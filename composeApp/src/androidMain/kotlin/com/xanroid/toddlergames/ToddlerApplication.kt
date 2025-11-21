package com.xanroid.toddlergames

import android.app.Application
import com.xanroid.toddlergames.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class ToddlerApplication(): Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@ToddlerApplication)
        }
    }
}