package com.example.doslistas.di

import com.example.doslistas.domain.useCases.AddTareaUseCase
import com.example.doslistas.domain.useCases.CargarTareasUseCase
import com.example.doslistas.domain.useCases.DeleteTareaUseCase

object AppModule {
    val addTareaUseCase: AddTareaUseCase = AddTareaUseCase()
    val cargarTareasUseCase: CargarTareasUseCase = CargarTareasUseCase()
    val deleteTareaUseCase: DeleteTareaUseCase = DeleteTareaUseCase()
}