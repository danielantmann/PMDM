package com.example.bibliotecacompose.domain.useCases

import com.example.bibliotecacompose.data.Libros
import com.example.bibliotecacompose.domain.model.Libro

class AddLibroUseCase {
    fun addLibro(l: Libro) = Libros.addLibro(l)
}