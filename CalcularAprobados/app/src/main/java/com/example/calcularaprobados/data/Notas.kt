package com.example.calcularaprobados.data

import com.example.calcularaprobados.domain.model.Nota

object Notas {

    private val notas = mutableListOf<Nota>()

    fun agregarNotas(nota: Nota){
        notas.add(nota)
    }

    fun dameTodasLasNotas(): List<Nota>{
        return notas
    }


}