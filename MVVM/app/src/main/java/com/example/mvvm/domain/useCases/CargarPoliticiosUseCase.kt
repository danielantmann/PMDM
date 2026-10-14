package com.example.mvvm.domain.useCases

import com.example.mvvm.data.Politicos

class CargarPoliticiosUseCase {

    fun cargarLista() = Politicos.dameLista()
}