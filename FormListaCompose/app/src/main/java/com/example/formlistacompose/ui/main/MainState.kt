package com.example.formlistacompose.ui.main

import com.example.formlistacompose.domain.model.Alumno

data class MainState(
    val alumnos: List<Alumno> = emptyList(),
    val alumno: Alumno = Alumno(
        nombre = "",
        apellido = "",
        curso = "",
        edad = "",
        email = "",
        isActive = true
    ),
    val loading: Boolean = false,
    val error: String? = null
)
