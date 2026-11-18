package com.example.doslistaxml.di

import com.example.doslistaxml.domain.useCases.DeleteTareaUseCase
import com.example.doslistaxml.domain.useCases.AddTareaUseCase
import com.example.doslistaxml.domain.useCases.CargarTareasUseCase

object AppModule {
    val addTareaUseCase: AddTareaUseCase = AddTareaUseCase()
    val cargarTareasUseCase: CargarTareasUseCase = CargarTareasUseCase()
    val deleteTareaUseCase: DeleteTareaUseCase = DeleteTareaUseCase()
}