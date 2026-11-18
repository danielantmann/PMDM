package com.example.composeexamcorregido.di

import com.example.composeexamcorregido.domain.useCases.AddAlumnoUseCase
import com.example.composeexamcorregido.domain.useCases.CargarAlumnosUseCase
import com.example.composeexamcorregido.domain.useCases.DeleteAlumnoUseCase

object AppMopdule {
    val addAlumnoUseCase: AddAlumnoUseCase = AddAlumnoUseCase()
    val cargarAlumnosUseCase: CargarAlumnosUseCase = CargarAlumnosUseCase()
    val deleteAlumnoUseCase: DeleteAlumnoUseCase = DeleteAlumnoUseCase()
}