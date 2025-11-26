package com.xanroid.toddlergames.di

import com.xanroid.toddlergames.memoryCardGame.ui.screen.MemoryCardGameScreen
import com.xanroid.toddlergames.memoryCardGame.ui.screen.MemoryCardMenuScreen
import com.xanroid.toddlergames.navigation.Navigator
import com.xanroid.toddlergames.navigation.Screen
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val navigationModule = module {

    single { Navigator(startDestination = Screen.MemoryCardMenu) }

    navigation<Screen.MemoryCardMenu> {
        MemoryCardMenuScreen(
            navigateToGame = { id ->
                get<Navigator>().navigateTo(destination = Screen.MemoryCardGame(gameTypeId = id))
            }
        )
    }

    navigation<Screen.MemoryCardGame> { route ->
        MemoryCardGameScreen(
            id = route.gameTypeId
         )
    }

}