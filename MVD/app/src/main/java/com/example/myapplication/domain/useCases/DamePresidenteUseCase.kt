package com.example.myapplication.domain.useCases

import com.example.myapplication.data.Politicos

class DamePresidenteUseCase {
    fun damePresidente() = Politicos.damePresidente()
}