package com.example.calcularipc.di

import com.example.calcularipc.domain.useCases.CalcularImcUseCase
import com.example.calcularipc.domain.useCases.ObtenerClasificacionImcUseCase

object AppModule {
    val calcularImcUseCase = CalcularImcUseCase()
    val clasificacionImcUseCase = ObtenerClasificacionImcUseCase()
}