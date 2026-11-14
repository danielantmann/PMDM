package com.example.controlinventariocompose.domain.useCases

import com.example.controlinventariocompose.data.Productos
import com.example.controlinventariocompose.domain.model.Producto

class AddProductoUseCase {
    fun addProducto(p: Producto) = Productos.addProducto(p)
}