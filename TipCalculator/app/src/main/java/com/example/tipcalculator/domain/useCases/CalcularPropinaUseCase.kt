package com.example.tipcalculator.domain.useCases

class CalcularPropinaUseCase {
    fun calcularPropinas(total: Double, porcentaje: Double): Double{

        return total+(total * porcentaje / 100)
    }
}