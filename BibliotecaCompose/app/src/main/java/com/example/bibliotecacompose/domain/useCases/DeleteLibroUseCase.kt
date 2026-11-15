package com.example.bibliotecacompose.domain.useCases

import com.example.bibliotecacompose.data.Libros
import com.example.bibliotecacompose.domain.model.Libro

class DeleteLibroUseCase {
    fun deleteLibro(l: Libro) = Libros.deleteLibro(l)
}