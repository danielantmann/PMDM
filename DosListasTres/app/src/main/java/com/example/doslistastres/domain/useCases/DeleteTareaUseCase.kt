package com.example.doslistastres.domain.useCases

import com.example.doslistastres.data.Tareas
import com.example.doslistastres.domain.model.Tarea

class DeleteTareaUseCase {
    fun deleteTarea(t: Tarea) = Tareas.deleteTarea(t);
}