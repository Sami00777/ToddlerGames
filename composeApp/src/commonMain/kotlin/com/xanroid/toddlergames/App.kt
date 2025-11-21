package com.xanroid.toddlergames

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.xanroid.toddlergames.MemoryCardGame.ui.screen.MemoryCardScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MemoryCardScreen()
    }
}