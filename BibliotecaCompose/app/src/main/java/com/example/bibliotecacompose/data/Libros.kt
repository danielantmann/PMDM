package com.example.bibliotecacompose.data

import com.example.bibliotecacompose.domain.model.Libro

object Libros {
    private var nextId = 1L

    private val libros = mutableListOf(
        Libro(
            id = nextId++,
            nombre = "El Hobbit",
            autor = "Tolkien",
            anio = 1939,
            categoria = "Ficcion",
            disponible = true
        )
    )

    fun getListaLibros(): List<Libro> = libros.toList()

    fun addLibro(l: Libro) {
        libros.add(l.copy(id = nextId++))
    }

    fun deleteLibro(l: Libro) {
        libros.removeAll { it.id == l.id }
    }
}