package com.example.cafeteriacompose.di

import com.example.cafeteriacompose.domain.useCases.AddPedidoUseCase
import com.example.cafeteriacompose.domain.useCases.CargarPedidosUseCase
import com.example.cafeteriacompose.domain.useCases.DeletePedidoUseCase

object AppModule {
    val addPedidoUseCase: AddPedidoUseCase = AddPedidoUseCase()
    val deletePedidoUseCase: DeletePedidoUseCase = DeletePedidoUseCase()
    val cargarPedidosUseCase: CargarPedidosUseCase = CargarPedidosUseCase()
}