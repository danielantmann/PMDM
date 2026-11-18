package com.example.doslistastres.domain.useCases

import com.example.doslistastres.data.Tareas

class CargarTareasUseCase {
    fun cargarTareas() = Tareas.getTareas();
}