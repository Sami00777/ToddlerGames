package com.xanroid.toddlergames.memoryCardGame.domain.usecase

import com.xanroid.toddlergames.memoryCardGame.domain.state.MatchCardResult
import com.xanroid.toddlergames.memoryCardGame.ui.model.MemoryCard

class MatchCardUseCase {
    operator fun invoke(
        cards: List<MemoryCard>,
        firstCard: MemoryCard,
        secondCard: MemoryCard? = null
    ): MatchCardResult {
        return when {
            secondCard == null -> {
                MatchCardResult.FirstCardSelected(
                    cards = cards.map { card ->
                        if (card.uniqueId == firstCard.uniqueId) {
                            card.copy(isFaceUp = true)
                        } else {
                            card
                        }
                    }
                )
            } else -> {
                when {
                    firstCard.uniqueId == secondCard.uniqueId -> {
                        MatchCardResult.SameCardClicked(cards)
                    }

                    firstCard.pairId == secondCard.pairId -> {
                        val updatedCards = cards.map { card ->
                            if(card.pairId == firstCard.pairId) {
                                card.copy(
                                    isMatched = true,
                                    isFaceUp = false
                                )
                            } else {
                                card
                            }
                        }

                        MatchCardResult.CardMatched(
                            cards = updatedCards,
                            isWinningMove = updatedCards.all { it.isMatched }
                        )

                    }
                    else -> {
                        MatchCardResult.CardDoNotMatch(
                            cards = cards.map { card ->
                                when(card.uniqueId){
                                    secondCard.uniqueId, firstCard.uniqueId -> card.copy(isFaceUp = true)
                                    else -> card
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}