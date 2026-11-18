package com.example.doslistasdos.domain.useCases

import com.example.doslistasdos.data.Tareas
import com.example.doslistasdos.domain.model.Tarea

class AddTareaUseCase {
    fun addTarea(t: Tarea) = Tareas.addTarea(t);
}