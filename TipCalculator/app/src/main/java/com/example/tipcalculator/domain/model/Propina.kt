package com.example.tipcalculator.domain.model

import java.util.UUID

data class Propina(
    val id: String =  UUID.randomUUID().toString(),
    val precio: Double,
    val porcentaje: Double,
    val resultado: Double
)
