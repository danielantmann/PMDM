package com.example.mvvm.domain.useCases

import com.example.mvvm.data.Politicos
import com.example.mvvm.domain.model.Politico

class DelPoliticoUseCase {
    fun delPolitico(p: Politico) = Politicos.del(p)
}