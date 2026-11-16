package com.example.mascotasgestioncompose.ui.main

import com.example.mascotasgestioncompose.domain.model.Mascota

data class MainState(
    val mascotas: List<Mascota> = emptyList(),
    val mascota: Mascota = Mascota(),
    val mascotaSelecionada: Mascota? = null,
    val error: String? = null,
    val mensaje: String? = null
)
