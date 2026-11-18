package com.example.doslistastres.data

import com.example.doslistastres.domain.model.Tarea

object Tareas {
    private var nextId = 4L
    private val tareas = mutableListOf(
        Tarea(1L, "Estudiar Compose", "Repasar estados", false),
        Tarea(2L, "Examen MVVM", "Arquitectura", false),
        Tarea(3L, "Proyecto Base", "Configurar Gradle", true)
    )

    fun getTareas() = tareas.toList()

    fun addTarea(t: Tarea) {
        // Buscamos si ya existe una tarea con el mismo ID
        val index = tareas.indexOfFirst { it.id == t.id }

        if (index != -1) {
            // SI EXISTE: Hacemos un UPDATE (reemplazamos la tarea vieja por la nueva modificada)
            tareas[index] = t
        } else {
            // SI NO EXISTE (es nueva): Le ponemos un ID automático y la añadimos
            val nuevaTarea = t.copy(id = if (t.id == 0L) nextId++ else t.id)
            if (t.id >= nextId) {
                nextId = t.id + 1
            }
            tareas.add(nuevaTarea)
        }
    }

    fun deleteTarea(t: Tarea) {
        tareas.removeAll { it.id == t.id }
    }
}