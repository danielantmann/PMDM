package com.example.conversorfarenheit.domain.useCases

import com.example.conversorfarenheit.data.HistorialConversiones
import com.example.conversorfarenheit.domain.model.Conversion

class ObtenerConversionesUseCase {
    fun obtenerConversiones(): List<Conversion>{
        return HistorialConversiones.obtenerConversiones()
    }
}