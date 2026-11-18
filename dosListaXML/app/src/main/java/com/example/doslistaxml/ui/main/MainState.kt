package com.example.doslistaxml.ui.main

import com.example.doslistaxml.domain.model.Tarea

data class MainState(
    val tareasPendientes: List<Tarea> = emptyList(),
    val tareasCompletadas: List<Tarea> = emptyList(),
    val tareaForm: Tarea = Tarea(),
    val tareaSeleccionada: Tarea? = null,
    val error: String? = null,
    val mensaje: String? = null
)
