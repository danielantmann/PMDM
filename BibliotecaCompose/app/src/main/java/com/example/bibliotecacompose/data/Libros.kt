package com.example.bibliotecacompose.data

import com.example.bibliotecacompose.domain.model.Libro

object Libros {
    private val libros = mutableListOf(
        Libro("El Hobbit","Tolkien", 1939,"Ficcion",true)
    )

    fun getListaLibros() = libros.toList()

    fun addLibro(l : Libro){
        libros.add(l)
    }

    fun deleteLibro(l: Libro){
        libros.remove(l)
    }
}