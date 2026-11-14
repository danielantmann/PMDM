package com.example.formlistacompose.data

import com.example.formlistacompose.domain.model.Alumno

object Alumnos {

    private val alumnos = mutableListOf(
        Alumno("Juan","Perez","2do","18","juan@pepe.com",true),
    )

    fun getListaAlumnos() = alumnos.toList()

    fun addAlumno(a: Alumno){
        alumnos.add(a)
    }

    fun delete(a: Alumno){
        alumnos.remove(a)
    }
}