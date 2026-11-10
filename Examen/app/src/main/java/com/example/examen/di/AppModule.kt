package com.example.examen.di

import com.example.examen.domain.useCases.AddAlumnoUseCase
import com.example.examen.domain.useCases.CargarListaAlumnosUseCase
import com.example.examen.domain.useCases.EliminarAlumnoUseCase

object AppModule {
    val addAlumnoUseCase: AddAlumnoUseCase = AddAlumnoUseCase()
    val cargarListaAlumnosUseCase: CargarListaAlumnosUseCase = CargarListaAlumnosUseCase()
    val eliminarAlumnoUseCase: EliminarAlumnoUseCase = EliminarAlumnoUseCase()
}