package com.example.todolist.domain.useCases

import com.example.todolist.data.Tareas

class EliminarTareaUseCase {
    fun eliminarTarea(id: String){
        Tareas.eliminarTarea(id)
    }
}