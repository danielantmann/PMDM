package com.example.bibliotecacompose.domain.model

data class Libro(
    val nombre: String,
    val autor: String,
    val anio: Int,
    val categoria: String,
    val disponible: Boolean,
)
