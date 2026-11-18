package com.example.doslistas.domain.useCases

import com.example.doslistas.data.Tareas
import com.example.doslistas.domain.model.Tarea

class AddTareaUseCase {
    fun addTarea(t: Tarea) = Tareas.addTarea(t);
}