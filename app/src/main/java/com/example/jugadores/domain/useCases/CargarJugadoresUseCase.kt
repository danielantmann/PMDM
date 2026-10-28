package com.example.jugadores.domain.useCases

import com.example.jugadores.data.Jugadores

class CargarJugadoresUseCase {
    fun cargarJugadores() = Jugadores.getLista()
}