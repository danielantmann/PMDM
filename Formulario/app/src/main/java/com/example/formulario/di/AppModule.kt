package com.example.formulario.di

import com.example.formulario.domain.useCases.AgregarUsuarioUseCase
import com.example.formulario.domain.useCases.DameUltimoUsuarioUseCase

object AppModule {
    val agregarUsuarioUseCase = AgregarUsuarioUseCase()
    val dameUltimoUsuarioUseCase = DameUltimoUsuarioUseCase()
}