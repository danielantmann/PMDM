package com.example.mvvm.data

import com.example.mvvm.domain.model.Politico

object Politicos {
    private val politicos = mutableListOf(
        Politico("Fernando", "Basura"),
        Politico("Maricarmen", "PAntera")
    )

    fun damePresidente(): Politico = politicos[1];
}