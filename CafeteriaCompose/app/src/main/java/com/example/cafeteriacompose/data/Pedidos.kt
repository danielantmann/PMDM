package com.example.cafeteriacompose.data

import com.example.cafeteriacompose.domain.model.Pedido

object Pedidos {
    private var nextId = 1L

    private val pedidos = mutableListOf(
        Pedido(
            id = nextId ++,
            nombre = "Roman Riquelme",
            producto = "Flat White",
            cantidad = 1,
            precio = 2.50,
            pagado = true,
        )
    )

    fun getListaPedidos() = pedidos.toList()

    fun addPedido(p: Pedido){
       pedidos.add(p.copy(id = nextId++))
    }

    fun deletePedido(p : Pedido){
        pedidos.removeAll { it.id == p.id }
    }
}