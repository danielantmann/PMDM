package com.example.tipcalculator.data

import com.example.tipcalculator.domain.model.Propina

object Propinas {
    private val listaResultados = mutableListOf<Propina>()

    fun guardarPropinas(propina: Propina){
        listaResultados.add(propina)
    }

    fun obtenerListaPropinas(): List<Propina>{
        return listaResultados
    }
}