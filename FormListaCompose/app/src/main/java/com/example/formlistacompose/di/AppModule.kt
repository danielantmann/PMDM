package com.example.formlistacompose.di

import com.example.formlistacompose.domain.useCases.AddAlumnoUseCase
import com.example.formlistacompose.domain.useCases.CargarAlumnosUseCase
import com.example.formlistacompose.domain.useCases.DeleteAlumnoUseCase

object AppModule {
    val addAlumnoUseCase: AddAlumnoUseCase = AddAlumnoUseCase()
    val cargarAlumnosUseCase: CargarAlumnosUseCase = CargarAlumnosUseCase()
    val deleteAlumnoUseCase: DeleteAlumnoUseCase = DeleteAlumnoUseCase()
}