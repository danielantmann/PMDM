package com.example.doslistasdos.data


import com.example.doslistasdos.domain.model.Tarea

object Tareas {
    private var nextId = 1L
    private val tareas = mutableListOf(
        Tarea(nextId++, "Estudiar Compose", "Repasar estados", false),
        Tarea(nextId++, "Examen MVVM", "Arquitectura", false)
    )

    fun getTareas() = tareas.toList()

    fun addTarea(t: Tarea) {
        tareas.add(t.copy(id = nextId++))
    }

    fun deleteTarea(t: Tarea) {
        tareas.removeAll { it.id == t.id }
    }
}