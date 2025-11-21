package com.xanroid.toddlergames.MemoryCardGame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xanroid.toddlergames.MemoryCardGame.data.repository.MemoryCardRepository
import com.xanroid.toddlergames.MemoryCardGame.ui.model.MemoryCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MemoryCardUiState(
    val listOfCard: List<MemoryCard> = emptyList(),
    val isReadyToMatch: Boolean = false,
    val isDialogShown: Boolean = false,
)

sealed class MemoryCardUiEvent {
    data object ShowingDialog: MemoryCardUiEvent()
}

sealed class MemoryCardAction {
    data class OnCardClicked(val memoryCard: MemoryCard) : MemoryCardAction()
    data object OnResetGame: MemoryCardAction()
}

class MemoryCardViewModel(
    private val memoryCardRepository: MemoryCardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoryCardUiState())
    val uiState = _uiState.asStateFlow()

    private val _isInPreview = MutableStateFlow(false)

    init {
        startNewGame()
        startPreview()
    }

    private fun startNewGame() {
        _uiState.update {
            it.copy(
                listOfCard = memoryCardRepository.getMemoryCardList(),
                isReadyToMatch = false,
                isDialogShown = false,
            )
        }
    }

    private fun startPreview() {
        viewModelScope.launch {
            _isInPreview.collectLatest { active ->
                if (active) {
                    delay(500)
                    endPreview()
                }
            }
        }
    }

    private fun endPreview() {
        _uiState.update { ui ->
            ui.copy(
                listOfCard = ui.listOfCard.map {
                    it.copy(isFaceUp = false)
                },
                isReadyToMatch = false,
            )
        }
        _isInPreview.update { false }
    }

    fun processAction(action: MemoryCardAction) {
        viewModelScope.launch {
            when (action) {
                is MemoryCardAction.OnCardClicked -> {
                    handleOnCardClicked(action.memoryCard)
                }
                is MemoryCardAction.OnResetGame -> {
                    startNewGame()
                }
            }
        }
    }

    private fun handleOnCardClicked(memoryCard: MemoryCard) {
        if (_isInPreview.value) {
            _isInPreview.update { false }
            _uiState.update { ui ->
                ui.copy(
                    listOfCard = ui.listOfCard.map {
                        it.copy(isFaceUp = it.isMatched)
                    },
                    isReadyToMatch = false,
                )
            }
        }
        if (!uiState.value.isReadyToMatch) {
            _uiState.update { ui ->
                ui.copy(
                    listOfCard = ui.listOfCard.map {
                        if (memoryCard.uniqueId == it.uniqueId) it.copy(isFaceUp = true) else it
                    },
                    isReadyToMatch = true
                )
            }
        } else {
            val firstCard = uiState.value.listOfCard.find { it.isFaceUp && !it.isMatched }
            if (firstCard?.uniqueId == memoryCard.uniqueId) return
            if ((firstCard?.pairId == memoryCard.pairId)) {
                _uiState.update { ui ->
                    ui.copy(
                        listOfCard = ui.listOfCard.map {
                            if (memoryCard.pairId == it.pairId) it.copy(
                                isFaceUp = true,
                                isMatched = true
                            ) else it.copy(isFaceUp = it.isMatched)
                        },
                        isReadyToMatch = false
                    )
                }
                val isWin = uiState.value.listOfCard.all { it.isMatched }
                if (isWin) {
                    _uiState.update { ui ->
                        ui.copy(
                            isDialogShown = true
                        )
                    }
                }
            } else {
                _isInPreview.update { true }
                _uiState.update { ui ->
                    ui.copy(
                        listOfCard = ui.listOfCard.map {
                            if (memoryCard.uniqueId == it.uniqueId) it.copy(isFaceUp = true) else it
                        },
                        isReadyToMatch = false
                    )
                }
            }
        }
    }
}
