package com.example.formlistacompose.domain.useCases

import com.example.formlistacompose.data.Alumnos

class CargarAlumnosUseCase {
    fun cargarAlumnos() = Alumnos.getListaAlumnos()
}