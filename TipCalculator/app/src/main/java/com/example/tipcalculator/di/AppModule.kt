package com.example.tipcalculator.di

import com.example.tipcalculator.domain.useCases.CalcularPropinaUseCase
import com.example.tipcalculator.domain.useCases.GuardarPropinasUseCase
import com.example.tipcalculator.domain.useCases.ObtenerListaPropinasUseCase

object AppModule {
    val calcularPropinaUseCase = CalcularPropinaUseCase()
    val guardarPropinasUseCase = GuardarPropinasUseCase()
    val obtenerListaPropinasUseCase = ObtenerListaPropinasUseCase()
}