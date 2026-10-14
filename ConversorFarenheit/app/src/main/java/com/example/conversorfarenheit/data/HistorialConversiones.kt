package com.example.conversorfarenheit.data

import com.example.conversorfarenheit.domain.model.Conversion

object HistorialConversiones {
    private val historialConversiones = mutableListOf<Conversion>()

    fun guardarConversiones(conversion: Conversion){
        historialConversiones.add(conversion)
    }

    fun obtenerConversiones() : List<Conversion>{
        return historialConversiones
    }
}