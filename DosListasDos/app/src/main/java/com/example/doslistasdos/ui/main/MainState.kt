package com.example.doslistasdos.ui.main

import com.example.doslistasdos.domain.model.Tarea

data class MainState (
    val tareasPendientes: List<Tarea> = emptyList(),
    val tareasCompletadas: List<Tarea> = emptyList(),
    val tarea: Tarea = Tarea(),
    val tareaSeleccionado: Tarea? = null,
    val error: String? = null,
    val mensaje : String? = null
)