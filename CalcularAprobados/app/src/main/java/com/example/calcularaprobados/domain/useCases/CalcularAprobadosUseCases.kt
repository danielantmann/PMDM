package com.example.calcularaprobados.domain.useCases

class CalcularAprobadosUseCases {

    fun calculaAprobado(nota: Int): String {
        if (nota > 4){
            return "aprobado"
        }else{
            return "suspenso"
        }
    }

}