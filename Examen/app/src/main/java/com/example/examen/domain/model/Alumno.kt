package com.example.examen.domain.model

data class Alumno(
    val nombre: String,
    val apellido : String,
    val curso: String,
    val edad : String,
    val correo: String,
    val isActivo: Boolean = true

)
