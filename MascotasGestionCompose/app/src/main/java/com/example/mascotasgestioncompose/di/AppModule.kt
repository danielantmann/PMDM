package com.example.mascotasgestioncompose.di

import com.example.mascotasgestioncompose.domain.useCases.AddMascotaUseCase
import com.example.mascotasgestioncompose.domain.useCases.CargarListaMascotasUseCase
import com.example.mascotasgestioncompose.domain.useCases.DeleteMascotaUseCase

object AppModule {
    val addMascotaUseCase: AddMascotaUseCase = AddMascotaUseCase()
    val deleteMascotaUseCase: DeleteMascotaUseCase = DeleteMascotaUseCase()
    val cargarListaMascotasUseCase: CargarListaMascotasUseCase = CargarListaMascotasUseCase()
}