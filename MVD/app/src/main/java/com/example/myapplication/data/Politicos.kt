package com.example.myapplication.data

import com.example.myapplication.domain.model.Politico

object Politicos {

    private  val politocos = mutableListOf(
        Politico("pepe","Basura",0),
        Politico("Juan","Basura",0),

    )

    fun damePresidente() = politocos[1];
}