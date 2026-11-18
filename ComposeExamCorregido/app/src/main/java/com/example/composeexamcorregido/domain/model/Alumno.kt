package com.example.composeexamcorregido.domain.model

data class Alumno(
    val id : Long = 0L,
    val nombre: String = "",
    val apellido: String = "",
    val curso: String = "",
    val edad: String = "",
    val email: String = "",
    val isActive: Boolean = false,
)
