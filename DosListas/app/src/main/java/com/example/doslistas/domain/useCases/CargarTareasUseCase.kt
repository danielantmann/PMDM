package com.example.doslistas.domain.useCases

import com.example.doslistas.data.Tareas

class CargarTareasUseCase {
    fun cargarTareas() = Tareas.getTareas();
}