package com.xanroid.toddlergames.memoryCardGame.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.xanroid.toddlergames.memoryCardGame.ui.component.FixedGrid
import com.xanroid.toddlergames.memoryCardGame.ui.component.VictoryDialog
import com.xanroid.toddlergames.memoryCardGame.ui.viewmodel.MemoryCardAction
import com.xanroid.toddlergames.memoryCardGame.ui.viewmodel.MemoryCardViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemoryCardScreen(
    modifier: Modifier = Modifier,
    viewmodel: MemoryCardViewModel = koinViewModel()
) {

    val uiState by viewmodel.uiState.collectAsState()

    if (uiState.isDialogShown){
        VictoryDialog(
            onDismissRequest = {
                viewmodel.processAction(MemoryCardAction.OnDismissDialog)
            },
            onPlayAgain = {
                viewmodel.processAction(MemoryCardAction.OnResetGame)
            },
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
