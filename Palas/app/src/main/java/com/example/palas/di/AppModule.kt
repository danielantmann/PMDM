package com.example.palas.di

import com.example.palas.domain.useCases.AddPalaUseCase
import com.example.palas.domain.useCases.CargarPalasUseCase
import com.example.palas.domain.useCases.DamePalaUseCase
import com.example.palas.domain.useCases.DeletePalaUseCase

object AppModule {
    val damePalaUseCase: DamePalaUseCase = DamePalaUseCase()
    val addPalaUseCase: AddPalaUseCase = AddPalaUseCase()
    val deletePalaUseCase: DeletePalaUseCase = DeletePalaUseCase()
    val cargarPalasUseCase: CargarPalasUseCase = CargarPalasUseCase()
}