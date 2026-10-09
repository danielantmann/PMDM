package com.example.formulario.data

import com.example.formulario.domain.model.Usuario

object Usuarios {

    private val usuarios = mutableListOf(
        Usuario("admin@email.com", "1234"),
        Usuario("pepe@email.com", "1567"),
        Usuario("juan@email.com", "00000"),
    )

    fun agregarUsuario(usuario: Usuario){
        usuarios.add(usuario)
    }

    fun dameUltimoUsuario(): Usuario? {
        return usuarios.lastOrNull()
    }
}