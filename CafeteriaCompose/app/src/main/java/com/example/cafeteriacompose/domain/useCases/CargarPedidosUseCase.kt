package com.example.cafeteriacompose.domain.useCases

import com.example.cafeteriacompose.data.Pedidos

class CargarPedidosUseCase {
    fun cargarPedidos() = Pedidos.getListaPedidos()
}