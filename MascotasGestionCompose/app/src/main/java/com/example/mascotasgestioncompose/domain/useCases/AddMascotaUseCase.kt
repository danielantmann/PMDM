package com.example.mascotasgestioncompose.domain.useCases

import com.example.mascotasgestioncompose.data.Mascotas
import com.example.mascotasgestioncompose.domain.model.Mascota

class AddMascotaUseCase {
    fun addMascota(m: Mascota) = Mascotas.addMascota(m)
}