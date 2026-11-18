package com.example.composeexamcorregido.domain.useCases

import com.example.composeexamcorregido.data.Alumnos

class CargarAlumnosUseCase {
    fun cargarAlumnos() = Alumnos.getListaAlumnos()
}