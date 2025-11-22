package com.xanroid.toddlergames.memoryCardGame.domain.state

import com.xanroid.toddlergames.memoryCardGame.ui.model.MemoryCard

sealed class MatchCardResult {
    abstract val cards: List<MemoryCard>
    data class FirstCardSelected(override val cards: List<MemoryCard>): MatchCardResult()
    data class SameCardClicked(override val cards: List<MemoryCard>): MatchCardResult()
    data class CardMatched(override val cards: List<MemoryCard>, val isWinningMove: Boolean): MatchCardResult()
    data class CardDoNotMatch(override val cards: List<MemoryCard>): MatchCardResult()
}