package com.example.mvvm.domain.useCases

import com.example.mvvm.data.Politicos
import com.example.mvvm.domain.model.Politico

class DamePresidenteUseCase {
    fun damePresidente() : Politico = Politicos.damePresidente();
}