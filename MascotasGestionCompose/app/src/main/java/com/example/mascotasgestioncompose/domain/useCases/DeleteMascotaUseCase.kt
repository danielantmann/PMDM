package com.example.mascotasgestioncompose.domain.useCases

import com.example.mascotasgestioncompose.data.Mascotas
import com.example.mascotasgestioncompose.domain.model.Mascota

class DeleteMascotaUseCase {
    fun deleteMascota(m: Mascota) = Mascotas.deleteMascota(m)
}