package com.example.palas.domain.useCases

import com.example.palas.data.Palas

class CargarPalasUseCase {
    fun cargarPalas() = Palas.getListaPalas()
}