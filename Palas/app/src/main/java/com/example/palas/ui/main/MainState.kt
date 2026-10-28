package com.example.palas.ui.main

import com.example.palas.domain.model.Pala

data class MainState(
    val pala: String,
    val palas: List<Pala>,
    val error: String?= null
)
