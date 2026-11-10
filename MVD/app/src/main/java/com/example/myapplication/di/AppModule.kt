package com.example.myapplication.di

import com.example.myapplication.domain.useCases.DamePresidenteUseCase
import com.example.myapplication.ui.main.MainViewModel

object AppModule {
    fun  provideMainViewModel(): MainViewModel = MainViewModel(damePresidenteUseCase)

    val damePresidenteUseCase: DamePresidenteUseCase = DamePresidenteUseCase();
}