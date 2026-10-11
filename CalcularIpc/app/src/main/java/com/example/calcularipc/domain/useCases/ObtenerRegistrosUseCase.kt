package com.example.calcularipc.domain.useCases

import com.example.calcularipc.data.ImcRegitros
import com.example.calcularipc.domain.model.ImcRegistro

class ObtenerRegistrosUseCase {
    fun obtenerRegistros(): List<ImcRegistro>{
        return ImcRegitros.obtenerRegistros()
    }
}