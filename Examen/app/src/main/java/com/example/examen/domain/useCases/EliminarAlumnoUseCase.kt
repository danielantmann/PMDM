package com.example.examen.domain.useCases

import com.example.examen.data.Alumnos
import com.example.examen.domain.model.Alumno

class EliminarAlumnoUseCase {
    fun deleteAlumno() = Alumnos.eliminaAlumno()
}