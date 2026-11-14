package com.example.controlinventariocompose.ui.main

import com.example.controlinventariocompose.domain.model.Producto

data class MainState(
    val productos: List<Producto> = emptyList(),
    val producto: Producto = Producto(
        nombre = "",
        categoria = "",
        precio = 0.00,
        stock = 0,
        disponible = false
    ),
    val error: String? = null
)
