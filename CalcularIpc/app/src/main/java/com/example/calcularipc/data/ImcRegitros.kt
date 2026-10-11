package com.example.calcularipc.data

import com.example.calcularipc.domain.model.ImcRegistro

object ImcRegitros {
    private val imcRegistros = mutableListOf<ImcRegistro>()

    fun agregarRegistro(imcRegistro: ImcRegistro){
        imcRegistros.add(imcRegistro)
    }

    fun obtenerRegistros(): List<ImcRegistro> {
        return imcRegistros
    }

    fun eliminarRegistros(id: String){
        imcRegistros.removeAll{it.id == id}
    }

}