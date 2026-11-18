package com.example.doslistasdos.domain.model

data class Tarea(
    val id: Long = 0L,
    val nombre: String = "",
    val descripcion: String = "",
    val isTerminada: Boolean = false,
)
