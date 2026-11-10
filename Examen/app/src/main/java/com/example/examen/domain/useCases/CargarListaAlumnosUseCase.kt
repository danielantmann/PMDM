package com.example.examen.domain.useCases

import com.example.examen.data.Alumnos

class CargarListaAlumnosUseCase {
    fun cargarLista()= Alumnos.obtenerListaDeAlumnos()
}