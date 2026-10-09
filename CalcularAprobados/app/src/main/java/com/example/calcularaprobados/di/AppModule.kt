package com.example.calcularaprobados.di

import com.example.calcularaprobados.domain.useCases.AgregarNotaUseCase
import com.example.calcularaprobados.domain.useCases.CalcularAprobadosUseCases
import com.example.calcularaprobados.domain.useCases.CalcularPromedioUseCase

object AppModule {
    val calcularAprobadosUseCases = CalcularAprobadosUseCases()
    val agregarNotaUseCase = AgregarNotaUseCase()
    val calcularPromedioUseCase = CalcularPromedioUseCase()
}