package com.example.jugadores.domain.useCases

import com.example.jugadores.data.Jugadores
import com.example.jugadores.domain.model.Jugador

class AddJugadorUseCase {
    fun addJugador(j: Jugador) = Jugadores.add(j)
}