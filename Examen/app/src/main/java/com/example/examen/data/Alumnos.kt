package com.example.examen.data

import com.example.examen.domain.model.Alumno

object Alumnos {
    private val listaAlumno = mutableListOf<Alumno>(
        Alumno("pablo", "perez", "segundo","18", "pablo.pablo.com")
    )

    fun addAlumno(alumno: Alumno){
        listaAlumno.add(alumno)
    }

    fun eliminaAlumno(){
        listaAlumno.removeFirstOrNull()
    }

    fun obtenerListaDeAlumnos(): List<Alumno>{
        return listaAlumno
    }
}