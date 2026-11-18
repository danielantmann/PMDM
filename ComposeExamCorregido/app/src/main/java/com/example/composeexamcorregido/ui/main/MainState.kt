package com.example.composeexamcorregido.ui.main

import com.example.composeexamcorregido.domain.model.Alumno

data class MainState(
    val alumnos: List<Alumno> = emptyList(),
    val alumno: Alumno = Alumno(),
    val loading: Boolean = false,
    val alumnoSeleccionado: Alumno? = null,
    val error: String? = null,
    val mensaje: String? = null
)
