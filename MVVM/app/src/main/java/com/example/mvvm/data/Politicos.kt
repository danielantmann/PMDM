package com.example.mvvm.data

import com.example.mvvm.domain.model.Politico
import kotlin.random.Random

object Politicos {


    private val politicos = mutableListOf(
        Politico("fernando","BASURILLAS",0),
        Politico("junca","Panteras Grises",0,)
    )


    fun damePresidente() = politicos[1]

    fun dameLista() = politicos.toList()
    fun add(p: Politico) {
        if ( Random.nextDouble()> 0.60)
            throw RuntimeException("Error al añadir")
        politicos.add(p)
    }

    fun del(p: Politico) {
        if ( Random.nextDouble()> 0.40)
            throw RuntimeException("Error al borrar")
        politicos.remove(p)

    }
}