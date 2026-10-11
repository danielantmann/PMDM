package com.example.calcularipc.domain.useCases

import kotlin.compareTo

class ObtenerClasificacionImcUseCase {
    fun clasificarImc(imc: Double): String{

        if (imc < 18.5){
            return "Bajo de peso, (IMC: ${imc})"
        }else if (imc > 18.5 && imc < 24.9){
            return "Normal, (IMC: ${imc})"
        }else{
            return  "Sobrepeso, (IMC: ${imc})"
        }
    }
}