package com.example.palas.domain.useCases

import com.example.palas.data.Palas
import com.example.palas.domain.model.Pala

class DeletePalaUseCase {
    fun deletePala(p: Pala) = Palas.delete(p)
}