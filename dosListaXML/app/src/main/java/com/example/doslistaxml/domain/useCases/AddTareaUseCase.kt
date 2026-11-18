package com.example.doslistaxml.domain.useCases

import com.example.doslistaxml.data.Tareas
import com.example.doslistaxml.domain.model.Tarea

class AddTareaUseCase {
    fun addTarea(t: Tarea) = Tareas.addTarea(t);
}