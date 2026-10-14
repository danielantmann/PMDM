package com.example.mvvm.domain.useCases

import com.example.mvvm.data.Politicos
import com.example.mvvm.domain.model.Politico

class AddPoliticoUseCase {

    fun addPolitico(p: Politico) = Politicos.add(p)
}