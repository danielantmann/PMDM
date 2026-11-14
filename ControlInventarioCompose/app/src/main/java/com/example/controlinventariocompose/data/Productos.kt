package com.example.controlinventariocompose.data

import com.example.controlinventariocompose.domain.model.Producto

object Productos {
    private val productos = mutableListOf(
        Producto("TV","Televisiones",150.00,15,true)
    )

    fun getListaProductos() = productos.toList()

    fun addProducto(p: Producto){
        productos.add(p)
    }

    fun deleteProductos(p: Producto){
        productos.remove(p)
    }
}