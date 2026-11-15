package com.example.bibliotecacompose.domain.useCases

import com.example.bibliotecacompose.data.Libros

class CargarLibrosUseCase {
    fun cargarLibros() = Libros.getListaLibros()
}