package com.example.conversorfarenheit.di

import com.example.conversorfarenheit.domain.useCases.ConvertirGradosUseCase
import com.example.conversorfarenheit.domain.useCases.GurdarConversionesUseCase
import com.example.conversorfarenheit.domain.useCases.ObtenerConversionesUseCase

object AppModule {
    val convertirGradosUseCase = ConvertirGradosUseCase()
    val gurdarConversionesUseCase = GurdarConversionesUseCase()
    val obtenerConversionesUseCase = ObtenerConversionesUseCase()
}