package com.example.composeexamcorregido.domain.useCases

import com.example.composeexamcorregido.data.Alumnos
import com.example.composeexamcorregido.domain.model.Alumno

class DeleteAlumnoUseCase {
    fun deleteAlumno(a: Alumno) = Alumnos.delete(a)
}