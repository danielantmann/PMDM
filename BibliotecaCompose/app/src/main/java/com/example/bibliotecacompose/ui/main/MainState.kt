package com.example.bibliotecacompose.ui.main

import com.example.bibliotecacompose.domain.model.Libro

data class MainState(
    val libros: List<Libro> = emptyList(),
    val libro: Libro = Libro(),
    val libroSeleccionado: Libro? = null,
    val error: String? = null,
    val mensaje: String?= null
)
