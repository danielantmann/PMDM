package com.example.palas.domain.useCases

import com.example.palas.data.Palas
import com.example.palas.domain.model.Pala

class AddPalaUseCase {
    fun addPala(p: Pala) = Palas.add(p)
}