package com.xanroid.toddlergames

import androidx.compose.ui.window.ComposeUIViewController
import com.xanroid.toddlergames.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }