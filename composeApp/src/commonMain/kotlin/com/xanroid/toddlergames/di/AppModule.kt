package com.xanroid.toddlergames.di

import com.xanroid.toddlergames.MemoryCardGame.data.repository.MemoryCardRepository
import com.xanroid.toddlergames.MemoryCardGame.data.repository.MemoryCardRepositoryImpl
import com.xanroid.toddlergames.MemoryCardGame.domain.usecase.MatchCardUseCase
import com.xanroid.toddlergames.MemoryCardGame.ui.viewmodel.MemoryCardViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin{
        config?.invoke(this)
        modules(
            sharedModule
        )
    }
}

val sharedModule = module {

    //ViewModels
    viewModel { MemoryCardViewModel(get(), get()) }

    //Repositories
    singleOf(::MemoryCardRepositoryImpl).bind<MemoryCardRepository>()

    //UseCases
    singleOf(::MatchCardUseCase)
}