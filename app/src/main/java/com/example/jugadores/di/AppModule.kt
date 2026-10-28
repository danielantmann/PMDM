package com.example.jugadores.di

import com.example.jugadores.domain.useCases.AddJugadorUseCase
import com.example.jugadores.domain.useCases.CargarJugadoresUseCase
import com.example.jugadores.domain.useCases.DameJugadorUseCase
import com.example.jugadores.domain.useCases.DelJugadorUseCase

object AppModule {
    val dameJugadorUseCase: DameJugadorUseCase = DameJugadorUseCase()
    val addJugadorUseCase: AddJugadorUseCase = AddJugadorUseCase()
    val delJugadorUseCase: DelJugadorUseCase = DelJugadorUseCase()
    val cargarJugadoresUseCase : CargarJugadoresUseCase = CargarJugadoresUseCase()

}