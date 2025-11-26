package com.xanroid.toddlergames.memoryCardGame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MemoryCardMenuScreen(
    modifier: Modifier = Modifier,
    navigateToGame: (gameType: Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navigateToGame(0)},
        ) {
            Text(
                text = "Color Match Game"
            )
        }
        Button(
            onClick = { navigateToGame(1)},
        ) {
            Text(
                text = "Animal Match Game"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryCardMenuScreenPreview(){
    MaterialTheme {
        MemoryCardMenuScreen(
            navigateToGame = {}
        )
    }
}
