package com.example.cafeteriacompose.domain.useCases

import com.example.cafeteriacompose.data.Pedidos
import com.example.cafeteriacompose.domain.model.Pedido

class AddPedidoUseCase {
    fun addPedido(p: Pedido) = Pedidos.addPedido(p)
}