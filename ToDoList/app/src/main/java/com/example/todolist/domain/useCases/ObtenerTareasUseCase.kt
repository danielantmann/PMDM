package com.example.todolist.domain.useCases

import com.example.todolist.data.Tareas
import com.example.todolist.domain.model.Tarea

class ObtenerTareasUseCase {
    fun obtenerTarea(): List<Tarea>{
        return Tareas.obtenerTarea()
    }
}