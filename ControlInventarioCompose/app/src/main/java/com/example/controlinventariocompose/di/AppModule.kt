package com.example.controlinventariocompose.di

import com.example.controlinventariocompose.domain.useCases.AddProductoUseCase
import com.example.controlinventariocompose.domain.useCases.CargarProductosUseCase
import com.example.controlinventariocompose.domain.useCases.DeleteProductoUseCase

object AppModule {
    val addProductoUseCase: AddProductoUseCase = AddProductoUseCase()
    val deleteProductoUseCase: DeleteProductoUseCase = DeleteProductoUseCase()
    val cargarProductosUseCase: CargarProductosUseCase = CargarProductosUseCase()
}