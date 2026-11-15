package com.example.bibliotecacompose.domain.model

data class Libro(
    val id: Long = 0L,
    val nombre: String = "",
    val autor: String = "",
    val anio: Int= 0,
    val categoria: String = "",
    val disponible: Boolean = false,
)
