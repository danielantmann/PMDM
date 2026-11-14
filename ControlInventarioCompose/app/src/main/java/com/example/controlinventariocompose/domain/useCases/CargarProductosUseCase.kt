package com.example.controlinventariocompose.domain.useCases

import com.example.controlinventariocompose.data.Productos

class CargarProductosUseCase {
    fun cargarProductos() = Productos.getListaProductos()
}