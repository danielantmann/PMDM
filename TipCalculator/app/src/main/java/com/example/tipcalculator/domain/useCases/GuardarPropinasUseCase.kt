package com.example.tipcalculator.domain.useCases

import com.example.tipcalculator.data.Propinas
import com.example.tipcalculator.domain.model.Propina

class GuardarPropinasUseCase {
    fun guardarPropinas(propina: Propina){
        return Propinas.guardarPropinas(propina)
    }
}