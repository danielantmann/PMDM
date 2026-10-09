package com.example.calcularaprobados.domain.useCases

import com.example.calcularaprobados.data.Notas.dameTodasLasNotas
import com.example.calcularaprobados.domain.model.Nota

class CalcularPromedioUseCase {
    fun calcularPromedio() : Double{
        val notas = dameTodasLasNotas()
        var resultado = 0.00
        for ( nota in notas) {
            resultado += nota.nota
        }
        if (! notas.isEmpty()){
            resultado = resultado / notas.size
            return resultado
        }
     return resultado
    }
}