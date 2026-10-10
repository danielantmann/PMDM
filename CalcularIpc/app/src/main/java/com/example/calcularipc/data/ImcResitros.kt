package com.example.calcularipc.data

import com.example.calcularipc.domain.model.ImcRegistro

object ImcResitros {
    private val imcRegistros = mutableListOf<ImcRegistro>()

    fun agregarRegistro(imcRegistro: ImcRegistro){
        imcRegistros.add(imcRegistro)
    }


}