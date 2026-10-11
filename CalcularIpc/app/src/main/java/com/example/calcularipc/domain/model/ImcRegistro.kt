package com.example.calcularipc.domain.model

import java.util.UUID

data class ImcRegistro(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val imc: Double,
    val resultado: String,
)
