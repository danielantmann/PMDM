package com.example.formulario.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.formulario.domain.model.Usuario
import com.example.formulario.domain.useCases.AgregarUsuarioUseCase
import com.example.formulario.domain.useCases.DameUltimoUsuarioUseCase

class MainViewModel(
    private  val dameUltimoUsuarioUseCase: DameUltimoUsuarioUseCase,
    private val agregarUsuarioUseCase: AgregarUsuarioUseCase,
) : ViewModel(){

    private val _state = MutableLiveData(MainState(textoMostrado = ""))
    val state: LiveData<MainState> = _state

    fun handleDameUltimoUsuario(){
        val usuario = dameUltimoUsuarioUseCase.dameUltimoUsuario()

        val texto =  "nombre: ${usuario?.nombre} , mensaje: ${usuario?.mensaje}"

        _state.value = _state.value?.copy(textoMostrado = texto)
            ?: MainState(textoMostrado = texto)
    }

    fun handleAgregarUsuario(nombre: String, mensaje: String){
        if (nombre.isBlank() || mensaje.isBlank()){
            _state.value = _state.value?.copy(error = "Por favor rellena todos los campos")
            return
        }

        val nuevoUsuario = Usuario(nombre,mensaje)
        agregarUsuarioUseCase.agregarUsuario(nuevoUsuario)

        val textoExito = "Guardado"
        _state.value = _state.value?.copy(
            textoMostrado = textoExito,
            error  = null
        )
    }

    class MainViewModelFactory(
        private val dameUltimoUsuarioUseCase: DameUltimoUsuarioUseCase,
        private val agregarUsuarioUseCase: AgregarUsuarioUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(dameUltimoUsuarioUseCase, agregarUsuarioUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}