package com.example.jugadores.ui.main

import com.example.jugadores.domain.model.Jugador

data class MainState (
    val jugador: String,
    val jugadores: List<Jugador>,
    val error: String?= null
)