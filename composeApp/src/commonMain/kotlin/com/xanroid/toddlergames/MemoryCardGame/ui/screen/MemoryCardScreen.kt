package com.xanroid.toddlergames.MemoryCardGame.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.xanroid.toddlergames.MemoryCardGame.ui.component.AlertDialogExample
import com.xanroid.toddlergames.MemoryCardGame.ui.component.FixedGrid
import com.xanroid.toddlergames.MemoryCardGame.ui.viewmodel.MemoryCardAction
import com.xanroid.toddlergames.MemoryCardGame.ui.viewmodel.MemoryCardViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemoryCardScreen(
    modifier: Modifier = Modifier,
    viewmodel: MemoryCardViewModel = koinViewModel()
) {

    val uiState by viewmodel.uiState.collectAsState()

    if (uiState.isDialogShown){
        AlertDialogExample(
            onDismissRequest = {

            },
            onConfirmation = {
                viewmodel.processAction(MemoryCardAction.OnResetGame)
            },
            dialogTitle = "You Won",
            dialogText = "Do you want to play again?",
            icon = Icons.Filled.Window
        )
    }

    FixedGrid(
        data = uiState.listOfCard,
        modifier = Modifier
            .fillMaxWidth(),
        onItemClicked = { cardData ->
            viewmodel.processAction(MemoryCardAction.OnCardClicked(cardData))
        }
    )
}
