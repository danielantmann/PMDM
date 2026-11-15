package com.example.cafeteriacompose.domain.useCases

import com.example.cafeteriacompose.data.Pedidos
import com.example.cafeteriacompose.domain.model.Pedido

class DeletePedidoUseCase {
    fun deletePedido(p: Pedido) = Pedidos.deletePedido(p)
}