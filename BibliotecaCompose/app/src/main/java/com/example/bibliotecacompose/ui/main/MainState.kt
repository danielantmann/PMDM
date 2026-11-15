package com.example.bibliotecacompose.ui.main

import com.example.bibliotecacompose.domain.model.Libro

data class MainState(
    val libros: List<Libro> = emptyList(),
    val libro: Libro = Libro(
        nombre = "",
        autor = "",
        anio = 0,
        categoria = "",
        disponible = false
    ),
    val libroSeleccionado: Libro? = null,
    val error: String? = null
)
