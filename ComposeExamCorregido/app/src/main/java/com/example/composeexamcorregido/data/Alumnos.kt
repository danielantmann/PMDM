package com.example.composeexamcorregido.data

import com.example.composeexamcorregido.domain.model.Alumno

object Alumnos {

    private var nextId =  1L
    private val alumnos = mutableListOf(
        Alumno( nextId ++,"Juan", "Perez", "2do", "18", "juan@pepe.com", true),
    )

    fun getListaAlumnos() = alumnos.toList()

    fun addAlumno(a: Alumno){
        alumnos.add(a.copy(id= nextId ++))
    }

    fun delete(a: Alumno){
        alumnos.removeAll { it.id == a.id }
    }
}