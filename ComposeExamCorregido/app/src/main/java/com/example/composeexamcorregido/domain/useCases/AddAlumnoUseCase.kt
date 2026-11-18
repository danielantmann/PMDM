package com.example.composeexamcorregido.domain.useCases

import com.example.composeexamcorregido.data.Alumnos
import com.example.composeexamcorregido.domain.model.Alumno

class AddAlumnoUseCase {
    fun addAlumno(a: Alumno) = Alumnos.addAlumno(a)
}