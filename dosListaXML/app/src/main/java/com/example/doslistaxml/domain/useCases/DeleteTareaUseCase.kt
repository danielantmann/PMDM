package com.example.doslistaxml.domain.useCases


import com.example.doslistaxml.domain.model.Tarea
import com.example.doslistaxml.data.Tareas

class DeleteTareaUseCase {
    fun deleteTarea(t: Tarea) = Tareas.deleteTarea(t);
}