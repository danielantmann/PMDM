package com.example.calcularipc.di

import com.example.calcularipc.domain.useCases.CalcularImcUseCase
import com.example.calcularipc.domain.useCases.EliminarRegistroUseCase
import com.example.calcularipc.domain.useCases.GuardarRegistroUseCase
import com.example.calcularipc.domain.useCases.ObtenerClasificacionImcUseCase
import com.example.calcularipc.domain.useCases.ObtenerRegistrosUseCase

object AppModule {
    val calcularImcUseCase = CalcularImcUseCase()
    val clasificacionImcUseCase = ObtenerClasificacionImcUseCase()
    val obtenerRegistrosUseCase = ObtenerRegistrosUseCase()
    val guardarRegistroUseCase = GuardarRegistroUseCase()
    val eliminarRegistroUseCase = EliminarRegistroUseCase()
}