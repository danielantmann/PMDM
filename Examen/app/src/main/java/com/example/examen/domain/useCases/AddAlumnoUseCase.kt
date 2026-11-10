package com.example.examen.domain.useCases

import com.example.examen.data.Alumnos
import com.example.examen.domain.model.Alumno

class AddAlumnoUseCase {
    fun addAlumno( a: Alumno) = Alumnos.addAlumno(a)
}