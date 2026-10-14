package com.example.conversorfarenheit.domain.useCases

import com.example.conversorfarenheit.data.HistorialConversiones
import com.example.conversorfarenheit.domain.model.Conversion

class GurdarConversionesUseCase {
    fun guardarConversiones(resultado: Conversion){
        return HistorialConversiones.guardarConversiones(resultado)
    }
}