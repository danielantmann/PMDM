package com.example.todolist.domain.useCases

import com.example.todolist.data.Tareas
import com.example.todolist.domain.model.Tarea

class GuardarTareaUseCase {
    fun guardarTarea(tarea: Tarea){
        Tareas.guardarTarea(tarea)
    }
}