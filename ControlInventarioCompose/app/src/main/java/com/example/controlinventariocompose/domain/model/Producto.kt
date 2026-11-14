package com.example.controlinventariocompose.domain.model

data class Producto (
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val disponible: Boolean
)

