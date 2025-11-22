package com.xanroid.toddlergames.memoryCardGame.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.xanroid.toddlergames.memoryCardGame.ui.model.MemoryCard

interface MemoryCardRepository {
    fun getMemoryCardList(): List<MemoryCard>
}

class MemoryCardRepositoryImpl: MemoryCardRepository {

    override fun getMemoryCardList(): List<MemoryCard> {
        val listOfColors = listOf(
            Color.Red,
            Color.Blue,
            Color.Gray,
            Color.Green,
            Color.Cyan,
            Color.Black,
        )

        val listOfIcons = listOf(
            Icons.Filled.Circle,
            Icons.Filled.Circle,
            Icons.Filled.Circle,
            Icons.Filled.Circle,
            Icons.Filled.Circle,
            Icons.Filled.Circle,
        )

        return buildCardList(
            icons = listOfIcons,
            colors = listOfColors
        )

    }

    private fun buildCardList(
        icons: List<ImageVector>,
        colors: List<Color>,
    ):List<MemoryCard> {
        val shuffledIcons = icons.shuffled()
        val shuffledColors = colors.shuffled()

        return shuffledIcons.mapIndexed { index, imageVector ->
            MemoryCard(
                pairId = index,
                icon = imageVector,
                color = shuffledColors[index],
            )
        }.let { list ->
            (list + list).shuffled().mapIndexed { index, data ->
                data.copy(
                    uniqueId = index
                )
            }
        }
    }

}