package com.example.doslistasdos.di

import com.example.doslistas.domain.useCases.DeleteTareaUseCase
import com.example.doslistasdos.domain.useCases.AddTareaUseCase
import com.example.doslistasdos.domain.useCases.CargarTareasUseCase

object AppModule {
    val addTareaUseCase: AddTareaUseCase = AddTareaUseCase()
    val cargarTareasUseCase: CargarTareasUseCase = CargarTareasUseCase()
    val deleteTareaUseCase: DeleteTareaUseCase = DeleteTareaUseCase()
}