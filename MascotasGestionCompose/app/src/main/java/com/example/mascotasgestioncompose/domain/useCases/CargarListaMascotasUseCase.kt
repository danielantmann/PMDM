package com.example.mascotasgestioncompose.domain.useCases

import com.example.mascotasgestioncompose.data.Mascotas

class CargarListaMascotasUseCase {
    fun cargarListaMascotas() = Mascotas.getListaMascotas()
}