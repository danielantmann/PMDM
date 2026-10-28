package com.example.palas.data

import com.example.palas.domain.model.Pala

object Palas {

    private val palas = mutableListOf(
        Pala("Knx", "Inter", 930),
        Pala("BullPadel", "Boca", 530),
        Pala("Palermo", "Boca", 630)
    )

    fun damePala() = palas[1]

    fun getListaPalas() = palas.toList()

    fun add(p: Pala){
        palas.add(p)
    }

    fun delete(p : Pala){
        palas.remove(p)
    }
}