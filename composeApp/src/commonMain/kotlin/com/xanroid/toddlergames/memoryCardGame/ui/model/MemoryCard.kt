package com.xanroid.toddlergames.memoryCardGame.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MemoryCard(
    val pairId: Int,
    val uniqueId: Int = 0,
    val icon: ImageVector = Icons.Default.Phone,
    val color: Color = Color.Black,
    val image: IMAGES = IMAGES.QUESTION_MARK,
    val isMatched: Boolean = false,
    val isFaceUp: Boolean = false,
)

enum class IMAGES {
    QUESTION_MARK,
    MONKEY,
    LION,
    EAGLE,
    RABBIT,
    ROOSTER,
    SHARK,
}