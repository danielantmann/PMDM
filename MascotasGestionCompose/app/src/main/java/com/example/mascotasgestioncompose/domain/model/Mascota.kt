package com.example.mascotasgestioncompose.domain.model

data class Mascota(
    val id: Long = 0L,
    val nombre: String = "",
    val especie: String = "",
    val edad: Int = 0,
    val dueño: String = "",
    val vacunado: Boolean = false,
)
