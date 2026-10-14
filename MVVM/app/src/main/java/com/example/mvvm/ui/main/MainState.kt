package com.example.mvvm.ui.main

import com.example.mvvm.domain.model.Politico

data class MainState (
    val presidente: String,
    val politicos: List<Politico>,
    val error: String? = null,
)