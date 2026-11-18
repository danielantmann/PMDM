package com.example.doslistas.domain.useCases

import com.example.doslistasdos.data.Tareas
import com.example.doslistasdos.domain.model.Tarea

class DeleteTareaUseCase {
    fun deleteTarea(t: Tarea) = Tareas.deleteTarea(t);
}