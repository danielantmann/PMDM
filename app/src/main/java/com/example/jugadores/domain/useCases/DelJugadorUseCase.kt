package com.example.jugadores.domain.useCases

import com.example.jugadores.data.Jugadores
import com.example.jugadores.domain.model.Jugador

class DelJugadorUseCase {
    fun delJugador(j: Jugador) = Jugadores.delete(j)
}