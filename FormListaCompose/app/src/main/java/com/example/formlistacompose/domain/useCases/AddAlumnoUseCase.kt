package com.example.formlistacompose.domain.useCases

import com.example.formlistacompose.data.Alumnos
import com.example.formlistacompose.domain.model.Alumno

class AddAlumnoUseCase {
    fun addAlumno(a: Alumno) = Alumnos.addAlumno(a)
}