package com.xanroid.toddlergames.memoryCardGame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xanroid.toddlergames.memoryCardGame.data.repository.MemoryCardRepository
import com.xanroid.toddlergames.memoryCardGame.domain.state.MatchCardResult
import com.xanroid.toddlergames.memoryCardGame.domain.usecase.MatchCardUseCase
import com.xanroid.toddlergames.memoryCardGame.ui.model.MemoryCard
import com.xanroid.toddlergames.memoryCardGame.utils.SoundPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MemoryCardUiState(
    val listOfCard: List<MemoryCard> = emptyList(),
    val isDialogShown: Boolean = false,
)

sealed class MemoryCardAction {
    data class OnCardClicked(val memoryCard: MemoryCard) : MemoryCardAction()
    data object OnResetGame: MemoryCardAction()
    data object OnDismissDialog: MemoryCardAction()
}

class MemoryCardViewModel(
    private val memoryCardRepository: MemoryCardRepository,
    private val matchCardUseCase: MatchCardUseCase,
    private val soundPlayer: SoundPlayer,
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
                is MemoryCardAction.OnDismissDialog -> {
                    _uiState.update { ui ->
                        ui.copy(isDialogShown = false)
                    }
                }
            }
        }
    }

    private suspend fun handleOnCardClicked(memoryCard: MemoryCard) {
        if (_isInPreview.value) {
            _isInPreview.update { false }
            endPreview()
        }

        val currentCards = uiState.value.listOfCard
        val firstCard = currentCards.find { it.isFaceUp && !it.isMatched }

        val result = matchCardUseCase(
            cards = uiState.value.listOfCard,
            firstCard = firstCard ?: memoryCard,
            secondCard = if (firstCard != null) memoryCard else null
        )

        when (result) {
            is MatchCardResult.FirstCardSelected -> {
                _uiState.update { ui ->
                    ui.copy(
                        listOfCard = result.cards,
                    )
                }
            }
            is MatchCardResult.SameCardClicked -> {
                // Do nothing
            }
            is MatchCardResult.CardMatched -> {
                soundPlayer.playSound("success.mp3")
                _uiState.update { ui ->
                    ui.copy(
                        listOfCard = result.cards,
                        isDialogShown = result.isWinningMove
                    )
                }
            }
            is MatchCardResult.CardDoNotMatch -> {
                soundPlayer.playSound("incorrect.mp3")
                _isInPreview.update { true }
                _uiState.update { ui ->
                    ui.copy(
                        listOfCard = result.cards,
                    )
                }
            }
        }
    }
}
