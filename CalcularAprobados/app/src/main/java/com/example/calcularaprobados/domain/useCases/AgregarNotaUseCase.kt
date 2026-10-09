package com.example.calcularaprobados.domain.useCases

import com.example.calcularaprobados.data.Notas
import com.example.calcularaprobados.domain.model.Nota

class AgregarNotaUseCase {
    fun agregarNota(nota: Nota){
        Notas.agregarNotas(nota)
    }
}