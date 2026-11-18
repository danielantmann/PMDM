package com.example.doslistaxml.domain.useCases

import com.example.doslistaxml.data.Tareas

class CargarTareasUseCase {
    fun cargarTareas() = Tareas.getTareas();
}