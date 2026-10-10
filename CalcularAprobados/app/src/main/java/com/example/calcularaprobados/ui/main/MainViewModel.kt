package com.example.calcularaprobados.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calcularaprobados.di.AppModule.calcularPromedioUseCase
import com.example.calcularaprobados.di.AppModule.agregarNotaUseCase


import com.example.calcularaprobados.domain.model.Nota
import com.example.calcularaprobados.domain.useCases.AgregarNotaUseCase
import com.example.calcularaprobados.domain.useCases.CalcularAprobadosUseCases
import com.example.calcularaprobados.domain.useCases.CalcularPromedioUseCase

class MainViewModel (
    private val calcularAprobadosUseCases: CalcularAprobadosUseCases,
    private val agregarNotaUseCase: AgregarNotaUseCase,
    private val calcularPromedioUseCase: CalcularPromedioUseCase,
): ViewModel(){

    private val _state = MutableLiveData(MainState(resultado = ""))
    val state: MutableLiveData<MainState> = _state


    fun handleCalcularNotas(textoNombre: String ,textoNota : String){
        if (textoNota.isNullOrBlank() || textoNombre.isNullOrBlank()){
            _state.value = _state.value?.copy(error= "Pro favor rellena los campos con numeros")
            return
        }

        val notaInt = textoNota.toIntOrNull()
        if (notaInt == null){
            _state.value = _state.value?.copy(error= "Pro favor rellena los campos con numeros")
            return
        }

        val resultado = calcularAprobadosUseCases.calculaAprobado(notaInt)
        val mensaje = "Alumno ${textoNombre} : ${resultado}"
        _state.value = _state.value?.copy(
            resultado=mensaje,
            error = null
        )

        agregarNotaUseCase.agregarNota(Nota(textoNombre,notaInt))
    }

    fun handleCalcularPromedio(){
        val promedio = calcularPromedioUseCase.calcularPromedio()
        val texto = "Promedio: ${promedio}"

        _state.value = _state.value?.copy(promedio = texto)
            ?: MainState(promedio = texto)
    }

    class MainViewModelFactory(
        private val calcularAprobadosUseCases: CalcularAprobadosUseCases
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.calcularaprobados.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(calcularAprobadosUseCases, agregarNotaUseCase ,calcularPromedioUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}