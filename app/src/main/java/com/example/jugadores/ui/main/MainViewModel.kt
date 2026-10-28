package com.example.jugadores.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jugadores.domain.model.Jugador
import com.example.jugadores.domain.useCases.AddJugadorUseCase
import com.example.jugadores.domain.useCases.CargarJugadoresUseCase
import com.example.jugadores.domain.useCases.DameJugadorUseCase
import com.example.jugadores.domain.useCases.DelJugadorUseCase

class MainViewModel (
    private val dameJugadorUseCase: DameJugadorUseCase,
    private val cargarJugadoresUseCase: CargarJugadoresUseCase,
    private val addJugadorUseCase: AddJugadorUseCase,
    private val delJugadorUseCase: DelJugadorUseCase,
): ViewModel() {
    private val _state = MutableLiveData<MainState>(MainState("Inicio", listOf()))
    val state: LiveData<MainState> = _state

    init {
        handleCargarLista()
    }

    fun handleDameJugador() {
        var jugador = dameJugadorUseCase.dameJugador()

        _state.value = _state.value?.copy(jugador = jugador.toString())
    }

    fun handleCargarLista() {
        _state.value = _state.value?.copy(jugadores = cargarJugadoresUseCase.cargarJugadores())
    }

    fun handleAdd(j: Jugador) {
        addJugadorUseCase.addJugador(j)
        _state.value = _state.value?.copy(jugadores = cargarJugadoresUseCase.cargarJugadores())
    }

    fun handleDelete(j : Jugador){
        delJugadorUseCase.delJugador(j)
        _state.value = _state.value?.copy(jugadores = cargarJugadoresUseCase.cargarJugadores())
    }

    class MainViewModelFactory(
        private val dameJugadorUseCase: DameJugadorUseCase,
        private val cargarJugadoresUseCase: CargarJugadoresUseCase,
        private val addJugadorUseCase: AddJugadorUseCase,
        private val delJugadorUseCase: DelJugadorUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.jugadores.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return MainViewModel(
                    dameJugadorUseCase,
                    cargarJugadoresUseCase,
                    addJugadorUseCase,
                    delJugadorUseCase,
                    ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}