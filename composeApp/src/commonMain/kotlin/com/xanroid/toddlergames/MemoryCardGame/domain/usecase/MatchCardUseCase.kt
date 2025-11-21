package com.xanroid.toddlergames.MemoryCardGame.domain.usecase

import com.xanroid.toddlergames.MemoryCardGame.ui.model.MemoryCard

class MatchCardUseCase {
    operator fun invoke(
        cards: List<MemoryCard>,
        firstCard: MemoryCard? = null,
        secondCard: MemoryCard? = null
    ): List<MemoryCard> {
        return when {
            secondCard == null && firstCard != null -> {
                cards.map {
                    if (it.uniqueId == firstCard.uniqueId) {
                        it.copy(isFaceUp = true)
                    } else {
                        it.copy(isFaceUp = it.isMatched)
                    }
                }
            } else -> {
                when {
                    firstCard?.uniqueId == secondCard?.uniqueId -> cards
                    firstCard?.pairId == secondCard?.pairId -> {
                        cards.map {
                            if(it.pairId == firstCard?.pairId) {
                                 it.copy(
                                     isMatched = true,
                                     isFaceUp = true
                                 )
                            } else {
                                it.copy(isFaceUp = it.isMatched)
                            }
                        }
                    }
                    else -> {
                        cards.map {
                            if(it.pairId == firstCard?.pairId) {
                                it.copy(
                                    isMatched = false,
                                    isFaceUp = it.isMatched
                                )
                            } else {
                                it
                            }
                        }
                    }
                }
            }
        }
    }
}