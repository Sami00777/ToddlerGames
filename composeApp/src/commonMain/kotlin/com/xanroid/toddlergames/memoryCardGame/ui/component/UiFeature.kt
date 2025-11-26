package com.xanroid.toddlergames.memoryCardGame.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.xanroid.toddlergames.memoryCardGame.ui.model.IMAGES
import com.xanroid.toddlergames.memoryCardGame.ui.model.MemoryCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import toddlergames.composeapp.generated.resources.Res
import toddlergames.composeapp.generated.resources.eagle
import toddlergames.composeapp.generated.resources.lion
import toddlergames.composeapp.generated.resources.monkey
import toddlergames.composeapp.generated.resources.question_mark
import toddlergames.composeapp.generated.resources.rabbit
import toddlergames.composeapp.generated.resources.rooster
import toddlergames.composeapp.generated.resources.shark

@Preview
@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    cardData: MemoryCard,
    onItemClicked: () -> Unit,
    type: CardType,
){
    val rotation by animateFloatAsState(
        targetValue = if (cardData.isFaceUp || cardData.isMatched) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    val isFront = rotation > 90f

    Box(
        modifier = modifier
            .padding(5.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onItemClicked()
            }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        contentAlignment = Alignment.Center
    ) {
        when(type) {
            CardType.COLOR -> {
                CardView(
                    card = cardData,
                    isFront = isFront,
                )
            }
            CardType.ANIMAL -> {
                ImageCardView(
                    card = cardData,
                    isFront = isFront,
                )
            }
        }

    }
}

@Composable
fun CardView(
    card: MemoryCard,
    isFront: Boolean,
) {
    val shape = RoundedCornerShape(5.dp)

    // Front and Back designs
    if (isFront) {
        // FRONT SIDE
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFfafafa),
                            Color(0xFFeaeaea)
                        )
                    )
                )
                .border(2.dp, Color.Black.copy(alpha = 0.15f), shape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = card.icon,
                tint = card.color,
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer { rotationY = 180f }
                    .fillMaxSize().padding(20.dp)
            )
        }

    } else {
        // BACK SIDE
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4A90E2),
                            Color(0xFF357ABD)
                        )
                    )
                )
                .border(2.dp, Color.Black.copy(alpha = 0.2f), shape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.QuestionMark,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(10.dp)
            )
        }
    }
}

@Composable
fun ImageCardView(
    card: MemoryCard,
    isFront: Boolean,
){
    val shape = RoundedCornerShape(5.dp)

    // Front and Back designs
    if (isFront) {
        // FRONT SIDE
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFE6BD5A),
                            Color(0xFFCA9A34)
                        )
                    )
                )
                .border(2.dp, Color.Black.copy(alpha = 0.15f), shape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = card.icon,
                tint = card.color,
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer { rotationY = 180f }
                    .fillMaxSize().padding(20.dp)
            )
            Image(
                modifier = Modifier.clip(CircleShape).border(5.dp, Color(0xFFB68126), CircleShape),
                painter = card.image.toPainter(),
                contentDescription = null,
            )
        }

    } else {
        // BACK SIDE
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFD3D3D2),
                            Color(0xFFBBBCB9)
                        )
                    )
                )
                .border(2.dp, Color.Black.copy(alpha = 0.2f), shape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.clip(CircleShape).border(8.dp, Color(0xFF6E6F6E), CircleShape),
                painter = painterResource(Res.drawable.question_mark),
                contentDescription = null,
            )
        }
    }
}


@Composable
fun IMAGES.toPainter() = when(this){
    IMAGES.QUESTION_MARK -> painterResource(Res.drawable.question_mark)
    IMAGES.MONKEY -> painterResource(Res.drawable.monkey)
    IMAGES.LION -> painterResource(Res.drawable.lion)
    IMAGES.EAGLE -> painterResource(Res.drawable.eagle)
    IMAGES.RABBIT -> painterResource(Res.drawable.rabbit)
    IMAGES.ROOSTER -> painterResource(Res.drawable.rooster)
    IMAGES.SHARK -> painterResource(Res.drawable.shark)
}

@Preview
@Composable
fun CardPreview(){
    CardView(
        card = MemoryCard(
            pairId = 0,
            uniqueId = 0,
            icon = Icons.Filled.QuestionMark,
            color = Color.Black,
            image = IMAGES.QUESTION_MARK,
            isMatched = false,
            isFaceUp = false,
        ),
        isFront = false
    )
}


@Composable
fun FixedGrid(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 3,
    data: List<MemoryCard>,
    onItemClicked: (MemoryCard) -> Unit,
    type: CardType = CardType.COLOR,
){
    BoxWithConstraints(
        modifier = modifier.fillMaxSize().padding(vertical = 60.dp),
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
                                },
                                type = type
                            )
                        }
                    }
                }
            }

        }
    }
}

enum class CardType(val id: Int) {
    COLOR(0),
    ANIMAL(1);

    companion object {
        fun getType(id: Int): CardType {
            return when(id){
                0 -> COLOR
                1 -> ANIMAL
                else -> COLOR
            }
        }
    }
}