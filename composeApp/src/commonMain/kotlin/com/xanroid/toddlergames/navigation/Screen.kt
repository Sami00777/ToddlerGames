package com.xanroid.toddlergames.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object MemoryCardMenu : Screen()

    @Serializable
    data class MemoryCardGame(val gameTypeId: Int) : Screen()

}