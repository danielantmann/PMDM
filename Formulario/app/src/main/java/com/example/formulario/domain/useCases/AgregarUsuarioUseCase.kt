package com.example.formulario.domain.useCases

import com.example.formulario.data.Usuarios
import com.example.formulario.domain.model.Usuario

class AgregarUsuarioUseCase {
    fun agregarUsuario( usuario  : Usuario){
        Usuarios.agregarUsuario(usuario)
    }

}