package com.example.controlinventariocompose.domain.useCases

import com.example.controlinventariocompose.data.Productos
import com.example.controlinventariocompose.domain.model.Producto

class DeleteProductoUseCase {
    fun deleteProducto(p: Producto) = Productos.deleteProductos(p)
}