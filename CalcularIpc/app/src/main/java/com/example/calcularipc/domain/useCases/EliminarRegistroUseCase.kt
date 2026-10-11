package com.example.calcularipc.domain.useCases

import com.example.calcularipc.data.ImcRegitros

class EliminarRegistroUseCase {
    fun eliminarRegistro(id: String){
        ImcRegitros.eliminarRegistros(id)
    }
}