package com.example.doslistas.domain.useCases

import com.example.doslistas.data.Tareas
import com.example.doslistas.domain.model.Tarea

class DeleteTareaUseCase {
    fun deleteTarea(t: Tarea) = Tareas.deleteTarea(t);
}