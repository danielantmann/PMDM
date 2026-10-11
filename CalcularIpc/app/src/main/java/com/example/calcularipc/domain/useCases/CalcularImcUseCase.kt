package com.example.calcularipc.domain.useCases

class CalcularImcUseCase {

    fun calcularImc(peso: Double, altura:Double): Double{
        return  peso/(altura * altura)

    }
}