package com.example.cafeteriacompose.domain.model

data class Pedido(
    val id: Long = 0L,
    val nombre: String = "",
    val producto: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.00,
    val pagado: Boolean = false,
)
