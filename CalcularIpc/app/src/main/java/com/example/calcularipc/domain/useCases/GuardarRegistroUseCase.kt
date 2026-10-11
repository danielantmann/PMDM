package com.example.calcularipc.domain.useCases

import com.example.calcularipc.data.ImcRegitros
import com.example.calcularipc.domain.model.ImcRegistro

class GuardarRegistroUseCase {
    fun guardarRegistro(registro: ImcRegistro){
        ImcRegitros.agregarRegistro(registro)
    }
}