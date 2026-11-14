package com.example.formlistacompose.domain.useCases

import com.example.formlistacompose.data.Alumnos
import com.example.formlistacompose.domain.model.Alumno

class DeleteAlumnoUseCase {
    fun deleteAlumno(a: Alumno) = Alumnos.delete(a)
}