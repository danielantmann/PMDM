package com.example.bibliotecacompose.di

import com.example.bibliotecacompose.domain.useCases.AddLibroUseCase
import com.example.bibliotecacompose.domain.useCases.CargarLibrosUseCase
import com.example.bibliotecacompose.domain.useCases.DeleteLibroUseCase

object AppModule {
    val addLibroUseCase: AddLibroUseCase = AddLibroUseCase()
    val deleteLibroUseCase: DeleteLibroUseCase = DeleteLibroUseCase()
    val cargarLibrosUseCase: CargarLibrosUseCase = CargarLibrosUseCase()
}