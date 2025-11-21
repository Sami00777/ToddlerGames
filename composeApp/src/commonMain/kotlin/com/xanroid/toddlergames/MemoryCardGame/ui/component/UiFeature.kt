package com.xanroid.toddlergames.MemoryCardGame.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xanroid.toddlergames.MemoryCardGame.ui.model.MemoryCard
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    cardData: MemoryCard,
    onItemClicked: () -> Unit,
){
    val data = if (cardData.isFaceUp || cardData.isMatched) cardData else MemoryCard(
        pairId = cardData.pairId,
        uniqueId = cardData.uniqueId,
        icon = Icons.Filled.QuestionMark,
        color = Color.Gray
    )
    Icon(
        modifier = modifier.padding(20.dp)
            .clickable(true) {
                onItemClicked()
            },
        imageVector = data.icon,
        contentDescription = "Circle Icon",
        tint = data.color
    )
}

@Composable
fun FixedGrid(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 3,
    data: List<MemoryCard>,
    onItemClicked: (MemoryCard) -> Unit,
){
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val cellHeight = maxHeight / rows
        val cellWidth = maxWidth / columns

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            repeat(rows) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cellHeight)
                ) {
                    repeat(columns) { columnIndex ->
                        val index = rowIndex * columns + columnIndex
                        if (index < data.size) {
                            val item = data[index]
                            GameCard(
                                modifier = Modifier.width(cellWidth).height(cellHeight),
                                cardData = item,
                                onItemClicked = {
                                    onItemClicked(item)
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun CustomCirclePreview() {
    GameCard(
        modifier = Modifier,
        cardData = MemoryCard(
            pairId = 1,
            uniqueId = 1,
            icon = Icons.Filled.Cloud,
            color = Color.Blue
        ),
        onItemClicked = {}
    )
}