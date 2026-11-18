package com.example.doslistastres.domain.useCases

import com.example.doslistastres.data.Tareas
import com.example.doslistastres.domain.model.Tarea

class AddTareaUseCase {
    fun addTarea(t: Tarea) = Tareas.addTarea(t);
}