package com.example.calcularipc.domain.useCases

class CalcularImcUseCase {

    fun calcularImc(peso: Double, altura:Double): String{
        val imc  = peso/(altura * altura)

        if (imc < 18.5){
            return "Bajo de peso, (IMC: ${imc})"
        }else if (imc > 18.5 && imc < 24.9){
            return "Normal, (IMC: ${imc})"
        }else{
            return  "Sobrepeso, (IMC: ${imc})"
        }
    }
}