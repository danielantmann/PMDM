package com.example.tipcalculator.ui.main

data class MainState (
    val resultado: Double? = 0.00,
    val totalGuardados: Int? = 0,
    val error: String? = null
)