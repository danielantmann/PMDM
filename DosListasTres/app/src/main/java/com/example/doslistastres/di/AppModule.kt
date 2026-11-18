package com.example.doslistastres.di

import com.example.doslistastres.domain.useCases.DeleteTareaUseCase
import com.example.doslistastres.domain.useCases.AddTareaUseCase
import com.example.doslistastres.domain.useCases.CargarTareasUseCase

object AppModule {
    val addTareaUseCase: AddTareaUseCase = AddTareaUseCase()
    val cargarTareasUseCase: CargarTareasUseCase = CargarTareasUseCase()
    val deleteTareaUseCase: DeleteTareaUseCase = DeleteTareaUseCase()
}