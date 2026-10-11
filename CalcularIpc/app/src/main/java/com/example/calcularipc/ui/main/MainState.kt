package com.example.calcularipc.ui.main

import com.example.calcularipc.domain.model.ImcRegistro

data class MainState(
    val resultadoImc: String = "",
    val error: String? = null,
    val listaImc: List<ImcRegistro> = emptyList()
)
