package com.example.mvvm.di

import com.example.mvvm.domain.useCases.AddPoliticoUseCase
import com.example.mvvm.domain.useCases.CargarPoliticiosUseCase
import com.example.mvvm.domain.useCases.DamePresidenteUseCase
import com.example.mvvm.domain.useCases.DelPoliticoUseCase
import com.example.mvvm.ui.main.MainViewModel

object AppModule {
    val damePresidenteUseCase: DamePresidenteUseCase = DamePresidenteUseCase()
    val addPoliticoUseCase: AddPoliticoUseCase = AddPoliticoUseCase()
    val delPoliticoUseCase: DelPoliticoUseCase = DelPoliticoUseCase()
    val cargarPoliticiosUseCase: CargarPoliticiosUseCase = CargarPoliticiosUseCase()
}