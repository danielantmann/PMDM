package com.example.doslistasdos.domain.useCases

import com.example.doslistasdos.data.Tareas

class CargarTareasUseCase {
    fun cargarTareas() = Tareas.getTareas();
}