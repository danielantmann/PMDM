package com.example.cafeteriacompose.ui.main

import com.example.cafeteriacompose.domain.model.Pedido

data class MainState(
    val pedidos: List<Pedido> = emptyList(),
    val pedido: Pedido = Pedido(),
    val pedidoSeleccionado: Pedido? =  null,
    val error: String? = null,
    val mensaje: String? = null
)
