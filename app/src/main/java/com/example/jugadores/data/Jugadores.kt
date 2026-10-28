package com.example.jugadores.data

import com.example.jugadores.domain.model.Jugador

object Jugadores {

    private val jugadores = mutableListOf(
        Jugador("Messi", "Inter", 930),
        Jugador("Maradona", "Boca", 530),
        Jugador("Palermo", "Boca", 630)
    )

    fun dameJugador() = jugadores[1]

    fun getLista() = jugadores.toList()

    fun add(j: Jugador){
        jugadores.add(j)
    }

    fun delete(j : Jugador){
        jugadores.remove(j)
    }

}