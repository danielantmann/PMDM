package com.example.todolist.ui.main

import com.example.todolist.domain.model.Tarea

data class MainState (
    val listaTarea: List<Tarea> = emptyList(),
    val error: String? = null
)