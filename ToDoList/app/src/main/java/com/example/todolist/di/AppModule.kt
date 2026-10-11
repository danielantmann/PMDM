package com.example.todolist.di

import com.example.todolist.domain.useCases.EliminarTareaUseCase
import com.example.todolist.domain.useCases.GuardarTareaUseCase
import com.example.todolist.domain.useCases.ObtenerTareasUseCase

object AppModule {
    val guardarTareaUseCase = GuardarTareaUseCase()
    val eliminarTareaUseCase = EliminarTareaUseCase()
    val obtenerTareasUseCase = ObtenerTareasUseCase()
}