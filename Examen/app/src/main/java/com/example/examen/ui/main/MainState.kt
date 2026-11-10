package com.example.examen.ui.main

import com.example.examen.domain.model.Alumno

data class MainState(
    val listaAlumnos: List<Alumno>,

    val error: String? = null,
)
